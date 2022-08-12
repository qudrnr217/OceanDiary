package com.oceandiary.api.room.service;

import com.oceandiary.api.common.exception.BusinessException;
import com.oceandiary.api.diary.dto.DiaryRequest;
import com.oceandiary.api.diary.entity.Stamp;
import com.oceandiary.api.diary.repository.StampRepository;
import com.oceandiary.api.file.entity.Image;
import com.oceandiary.api.file.repository.ImageRepository;
import com.oceandiary.api.file.service.ImageService;
import com.oceandiary.api.file.service.S3Service;
import com.oceandiary.api.room.entity.*;
import com.oceandiary.api.room.exception.PasswordNotValidException;
import com.oceandiary.api.room.repository.*;
import com.oceandiary.api.room.dto.RoomRequest;
import com.oceandiary.api.room.dto.RoomResponse;
import com.oceandiary.api.user.entity.User;
import io.openvidu.java.client.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Transactional
@Slf4j
public class RoomService {
    // OpenVidu object as entrypoint of the SDK
    private final OpenVidu openVidu;
    // URL where our OpenVidu server is listening
    private final String OPENVIDU_URL;
    // Secret shared with our OpenVidu server
    private final String SECRET;
    private final S3Service s3Service;
    private final ImageService imageService;
    private final RoomRepository roomRepository;
    private final RoomOnRedisRepository roomOnRedisRepository;
    private final ParticipantRepository participantRepository;
    private final ParticipantOnRedisRepository participantOnRedisRepository;
    private final DropoutRepository dropoutRepository;
    private final ImageRepository imageRepository;
    private final StampRepository stampRepository;

    @Value("${openvidu.secret}")
    private String OPENVIDU_SECRET;

    public RoomService(@Value("${openvidu.secret}") String secret,
                       @Value("${openvidu.url}") String openviduUrl,
                       RoomRepository roomRepository,
                       RoomOnRedisRepository roomOnRedisRepository,
                       ParticipantRepository participantRepository,
                       ParticipantOnRedisRepository participantOnRedisRepository,
                       DropoutRepository dropoutRepository,
                       ImageRepository imageRepository,
                       S3Service s3Service,
                       ImageService imageService,
                       StampRepository stampRepository) {
        this.SECRET = secret;
        this.OPENVIDU_URL = openviduUrl;
        this.roomRepository = roomRepository;
        this.roomOnRedisRepository = roomOnRedisRepository;
        this.participantRepository = participantRepository;
        this.participantOnRedisRepository = participantOnRedisRepository;
        this.dropoutRepository = dropoutRepository;
        this.imageRepository = imageRepository;
        this.s3Service = s3Service;
        this.imageService = imageService;
        this.stampRepository = stampRepository;
        this.openVidu = new OpenVidu(OPENVIDU_URL, SECRET);
    }

    public RoomResponse.CreateRoom createRoom(RoomRequest.CreateRoom request, MultipartFile file, User user) {

        String serverData = "{\"userId\": \"" + user.getId() + "\"," +
                "\"name\": \"" + user.getName() + "\"}";
        ConnectionProperties connectionProperties =
                new ConnectionProperties.Builder().type(ConnectionType.WEBRTC).data(serverData).role(OpenViduRole.MODERATOR).build();

        log.info("OpenVidu connection successful");

        if (roomOnRedisRepository.findByCreatedBy(user.getId()).isPresent()) {
            throw new BusinessException("이미 방을 생성한 유저가 다시 생성함");
        }

        try {

            Image newImage = null;
            // AWS S3 image upload
            if (!file.isEmpty()) {
                Long imageId = imageService.save(file).getId();
                newImage = imageRepository.findById(imageId).orElseThrow();
            }

            Room room = Room.builder()
                    .category(request.getCategoryId())
                    .user(user)
                    .title(request.getTitle())
                    .rule(request.getRule())
                    .image(newImage)
                    .maxNum(request.getMaxNum())
                    .isOpen(request.getIsOpen())
                    .pw(request.getPw())
                    .build();

            Room newRoom = roomRepository.save(room);
            log.info("Session created: {}", newRoom);

            // OpenVidu API: Create a new OpenVidu Session
            Session session = this.openVidu.createSession();
            // Generate a new Connection with the recently created connectionProperties
            Connection connection = session.createConnection(connectionProperties);
            String token = connection.getToken();

            RoomOnRedis newRoomOnRedis = RoomOnRedis.builder().id(newRoom.getId()).sessionId(session.getSessionId()).createdBy(user.getId()).build();
            roomOnRedisRepository.save(newRoomOnRedis);
            ParticipantOnRedis participantOnRedis = ParticipantOnRedis.builder()
                    .id(newRoom.getId())
                    .participantTokenMap(new ConcurrentHashMap<>())
                    .participantConnectionMap(new ConcurrentHashMap<>())
                    .build();
            ParticipantOnRedis newParticipantOnRedis = participantOnRedisRepository.save(participantOnRedis);

            Participant newParticipant = Participant.builder()
                    .room(newRoom)
                    .user(user)
                    .name(user.getName())
                    .enterDate(LocalDateTime.now())
                    .build();

            newParticipant = participantRepository.save(newParticipant);

            newParticipantOnRedis.addParticipant(newParticipant.getId(), token, connection.getConnectionId());
            participantOnRedisRepository.save(newParticipantOnRedis);

            return RoomResponse.CreateRoom.builder()
                    .roomId(newRoom.getId())
                    .participantId(newParticipant.getId())
                    .token(token)
                    .connectionId(connection.getConnectionId())
                    .build();

        } catch (Exception e) {
            throw new BusinessException("세션 생성중 예외 발생");
        }
    }

    /**
     * 1. 참가자가 이전에 강퇴당한 사람인지 확인한다.
     * 2. 현재 방의 curNum이 maxNum이 되었다면 입장할 수 없다.
     */
    public RoomResponse.EnterRoom enterRoom(RoomRequest.EnterRoom request, Long roomId, User user, String name) {

        String serverData = "{\"userId\": \"" + (user != null ? "" + user.getId().toString() : "null") + "\"," + "\"name\": \"" + (user != null ? user.getName() : name) + "\"}";
        ConnectionProperties connectionProperties =
                new ConnectionProperties.Builder().type(ConnectionType.WEBRTC).data(serverData).role(OpenViduRole.PUBLISHER).build();

        if (user != null && dropoutRepository.findByUser_idAndRoom_id(user.getId(), roomId).isPresent()) {
            throw new BusinessException("이미 강퇴당한 사용자는 입장할 수 없습니다.");
        }

        try {
            String sessionId = roomOnRedisRepository.findById(roomId).orElseThrow().getSessionId();
            Session session = getSession(sessionId);

            Connection connection = session.createConnection(connectionProperties);
            String token = connection.getToken();
            Room room = roomRepository.findById(roomId).orElseThrow();

            // 비밀번호 설정된 방인데 비밀번호가 틀린경우
            if (!room.getIsOpen() && !room.getPw().equals(request.getPw())) {
                throw new PasswordNotValidException();
            }

            Participant newParticipant = Participant.builder()
                    .room(room)
                    .user(user != null ? user : null)
                    .name(user != null ? user.getName() : name)
                    .enterDate(LocalDateTime.now())
                    .build();

            newParticipant = participantRepository.save(newParticipant);

            ParticipantOnRedis participantOnRedis = participantOnRedisRepository.findById(roomId).orElseThrow();
            participantOnRedis.addParticipant(newParticipant.getId(), token, connection.getConnectionId());
            participantOnRedisRepository.save(participantOnRedis);

            return RoomResponse.EnterRoom.builder()
                    .participantId(newParticipant.getId())
                    .token(token)
                    .connectionId(connection.getConnectionId())
                    .build();

        } catch (OpenViduJavaClientException e) {
            // If internal error generate an error message and return it to client
            throw new BusinessException(e.getMessage());
        } catch (OpenViduHttpException e) {
            handleUnexpectedlyInvalidSessionException(roomId, e);
            throw new BusinessException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 1. 방장이 나갈 경우 세션을 종료한다.
     */
    public RoomResponse.OnlyId exitRoom(Long roomId, Long participantId, User user) {

        try {
            RoomOnRedis roomOnRedis = roomOnRedisRepository.findById(roomId).orElseThrow();
            ParticipantOnRedis participantOnRedis = participantOnRedisRepository.findById(roomId).orElseThrow();

            Session session = getSession(roomOnRedis.getSessionId());

            Room room = roomRepository.findById(roomId).orElseThrow();

            if (user != null && user.getId() == roomOnRedis.getCreatedBy()) {  // 방장이 나갈경우
                for (Long pid : participantOnRedis.getParticipantTokenMap().keySet()) {
                    addStampAndUpdateVisitedAtBeforeExit(room, pid);
                }
                participantOnRedis.getParticipantTokenMap().clear();
                participantOnRedis.getParticipantConnectionMap().clear();
            } else {
                addStampAndUpdateVisitedAtBeforeExit(room, participantId);
            }

            participantOnRedis.removeParticipant(participantId);

            if (participantOnRedis.getParticipantTokenMap().isEmpty()) {  // 방에 더이상 참가자가 없을경우
                // 세션 종료
                session.close();
                participantOnRedisRepository.deleteById(roomId);
                roomOnRedisRepository.deleteById(roomId);
                room = roomRepository.findById(roomId).orElseThrow();
                room.setDeletedAt(LocalDateTime.now());
                log.info("Session destroyed");
            } else {  // 방에 아직 참가자가 있을경우
                participantOnRedisRepository.save(participantOnRedis);
            }

        } catch (OpenViduJavaClientException e) {
            // If internal error generate an error message and return it to client
            throw new BusinessException(e.getMessage());
        } catch (OpenViduHttpException e) {
            handleUnexpectedlyInvalidSessionException(roomId, e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return RoomResponse.OnlyId.builder().roomId(roomId).build();
    }

    /**
     * default 5개의 방목록을 첫페이지일 경우 가장 최신 것중 5개를 가져온다.
     * 그 다음부터는 마지막에 가져온 roomId보다 작은 5개를 가져온다.
     * 조건: Redis안에 룸이 존재하는 방들만 가져온다.
     */
    public Page<RoomResponse.SearchRooms> search(RoomRequest.RoomSearchCondition condition, Pageable pageable) {
        Page<RoomResponse.SearchRooms> page = roomRepository.search(condition, pageable);
        Iterator<RoomResponse.SearchRooms> searchedRoomsIterator = page.getContent().iterator();
        boolean isInConsistent = false;
        while (searchedRoomsIterator.hasNext()) {
            RoomResponse.SearchRooms searchedRoom = searchedRoomsIterator.next();
            Optional<ParticipantOnRedis> participantOrNull = participantOnRedisRepository.findById(searchedRoom.getRoomId());
            // redis와 mysql의 데이터 정합성 수행
            if (participantOrNull.isEmpty()) {
                // 1. 데이터 부정합성 발생
                isInConsistent = true;
                // 2. 부정합성 해결 -> Room 엔티티의 deletedAt에 현재시각을 추가
                resolveInconsistency();
                break;
            }
            // 현재 참가자 넣기
            Integer curNum = participantOrNull.orElseThrow().getParticipantTokenMap().size();
            searchedRoom.setCurNum(curNum);
        }
        // 만약 부정합성이 발견되었다면 다시 가져온다.
        if (isInConsistent) {
            page = roomRepository.search(condition, pageable);
            for (RoomResponse.SearchRooms searchedRoom : page.getContent()) {
                Integer curNum = participantOnRedisRepository.findById(searchedRoom.getRoomId())
                        .map(participantOnRedis -> participantOnRedis.getParticipantTokenMap().size())
                        .orElse(0);
                searchedRoom.setCurNum(curNum);
            }
        }
        return page;
    }

    @Transactional(readOnly = true)
    public RoomResponse.RoomInfo getRoomInfo(Long roomId) {
        RoomOnRedis roomOnRedis = roomOnRedisRepository.findById(roomId).orElseThrow();
        ParticipantOnRedis participantOnRedis = participantOnRedisRepository.findById(roomId).orElseThrow();
        Room room = roomRepository.findById(roomId).orElseThrow();
        return RoomResponse.RoomInfo.builder()
                .roomId(roomId)
                .sessionId(roomOnRedis.getSessionId())
                .categoryId(room.getCategory())
                .createdBy(room.getCreatedBy().getId())
                .title(room.getTitle())
                .rule(room.getRule())
                .imageId(room.getImage() != null ? room.getImage().getId() : null)
                .maxNum(room.getMaxNum())
                .curNum(participantOnRedis.getParticipantTokenMap().size())
                .isOpen(room.getIsOpen())
                .build();
    }

    @Transactional(readOnly = true)
    public RoomResponse.RoomDetail getRoomDetail(Long roomId) {
        return RoomResponse.RoomDetail.builder()
                .roomInfo(getRoomInfo(roomId))
                .participantList(getParticipants(roomId))
                .build();
    }

    @Transactional(readOnly = true)
    public List<RoomResponse.RoomDetail.ParticipantInfo> getParticipants(Long roomId) {
        ParticipantOnRedis participantOnRedis = participantOnRedisRepository.findById(roomId).orElseThrow();
        Map<Long, String> participantTokenMap = participantOnRedis.getParticipantTokenMap();
        Map<Long, String> participantConnectionMap = participantOnRedis.getParticipantConnectionMap();
        List<RoomResponse.RoomDetail.ParticipantInfo> participantInfos = participantRepository.findActiveParticipants(participantTokenMap.keySet());
        participantInfos.forEach(participantInfo -> {
            participantInfo.setToken(participantTokenMap.get(participantInfo.getParticipantId()));
            participantInfo.setConnectionId(participantConnectionMap.get(participantInfo.getParticipantId()));
        });
        return participantInfos;
    }

    /**
     * 1. 수정하는 사람이 방장인지 확인한다.
     * 2. 이미지 파일 수정인지 확인한다.
     * 3. maxNum이 curNum보다 큰지 확인한다. 크거나 같은 경우만 수정 가능하게 한다.
     */
    public RoomResponse.OnlyId updateRoomInfo(Long roomId, RoomRequest.UpdateRoom request, User user) {

        if (isValidated(roomId, user)) {
            throw new BusinessException("방 수정 권한이 없습니다.");
        }

        if (participantOnRedisRepository.findById(roomId).orElseThrow().getParticipantTokenMap().size() > request.getMaxNum()) {
            throw new BusinessException("현재 참가 인원보다 적게 설정할 수 없습니다.");
        }

        Room foundRoom = roomRepository.findById(roomId).orElseThrow();
        foundRoom.updateInfo(request);
        return RoomResponse.OnlyId.builder().roomId(roomId).build();
    }
    public RoomResponse.OnlyId updateRoomImage(Long roomId, MultipartFile file, User user) {

        if (isValidated(roomId, user)) {
            throw new BusinessException("방 수정 권한이 없습니다.");
        }

        Image newImage = null;
        if (!file.isEmpty()) {
            Long newImageId = imageService.save(file).getId();
            newImage = imageRepository.findById(newImageId).orElseThrow();
        }

        Room foundRoom = roomRepository.findById(roomId).orElseThrow();
        foundRoom.updateImage(newImage);
        return RoomResponse.OnlyId.builder().roomId(roomId).build();
    }

    private boolean isValidated(Long roomId, User user) {
        if(roomRepository.findById(roomId).orElseThrow().getUser().getId() != user.getId())
            return false;
        return true;
    }
    /**
     * 1. 같퇴하는 주체가 방장인지 확인한다.
     * 2. 강퇴자가 아직 세션에 남아있는지 확인한다.
     * 3. participant의 exit_date를 기록한다.
     * 4. 강퇴자는 스탬프를 얻을 수 없다.
     */
    public RoomResponse.OnlyId dropoutParticipant(Long roomId, Long participantId, User user) {

        if (roomOnRedisRepository.findById(roomId).orElseThrow().getCreatedBy() != user.getId()) {
            throw new BusinessException("방장만이 강퇴하기 기능을 사용할 수 있습니다.");
        }

        if (participantOnRedisRepository.findById(roomId).orElseThrow().getParticipantTokenMap().get(participantId) == null) {
            throw new BusinessException("이미 퇴장한 참가자이므로 강퇴할 수 없습니다.");
        }

        try {
            Session session = getSession(roomOnRedisRepository.findById(roomId).orElseThrow().getSessionId());
            ParticipantOnRedis participants = participantOnRedisRepository.findById(roomId).orElseThrow();
            String connectionId = participants.getParticipantConnectionMap().get(participantId);
            // OpenVidu API: force disconnect participant
            session.forceDisconnect(connectionId);
            participants.getParticipantTokenMap().remove(participantId);
            participants.getParticipantConnectionMap().remove(participantId);
            participants = participantOnRedisRepository.save(participants);
            Participant participant = participantRepository.findById(participantId).orElseThrow();
            participant.addExitDate();
            Dropout dropout = Dropout.builder()
                    .room(roomRepository.findById(roomId).orElseThrow())
                    .user(participant.getUser())
                    .name(participant.getName())
                    .build();
            dropoutRepository.save(dropout);
            if (participants.getParticipantTokenMap().isEmpty()) {  // 방에 더이상 참가자가 없을경우
                // 세션 종료
                session.close();
                participantOnRedisRepository.deleteById(roomId);
                roomOnRedisRepository.deleteById(roomId);
                Room room = roomRepository.findById(roomId).orElseThrow();
                room.setDeletedAt(LocalDateTime.now());
                log.info("Session destroyed");
            }
        } catch (OpenViduJavaClientException | OpenViduHttpException e) {
            throw new RuntimeException(e);
        }
        return RoomResponse.OnlyId.builder().roomId(roomId).build();
    }

    private void createStamp(DiaryRequest.StampCreate request, User user) {
        Stamp stamp = Stamp.create(user, request.getCategory(), request.getEnterTime(), request.getExitTime());
        stampRepository.save(stamp);
    }

    private Session getSession(String sessionId) {
        List<Session> activeSessions = this.openVidu.getActiveSessions();
        return activeSessions.stream().filter(s -> s.getSessionId().equals(sessionId)).findFirst().orElseThrow();
    }

    private void handleUnexpectedlyInvalidSessionException(Long roomId, OpenViduHttpException e) {
        if (404 == e.getStatus()) {
            // Invalid sessionId (user left unexpectedly). Session object is not valid
            // anymore. Clean collections and continue as new session
            roomOnRedisRepository.deleteById(roomId);
            participantOnRedisRepository.deleteById(roomId);
            Room room = roomRepository.findById(roomId).orElseThrow();
            room.setDeletedAt(LocalDateTime.now());
        }
    }

    private void addStampAndUpdateVisitedAtBeforeExit(Room room, Long participantId) {
        Participant participant = participantRepository.findById(participantId).orElseThrow();
        participant.addExitDate();

        if (participant.getUser() != null) {
            // 스탬프 저장
            createStamp(DiaryRequest.StampCreate.builder()
                    .category(room.getCategory())
                    .enterTime(participant.getEnterDate())
                    .exitTime(participant.getExitDate())
                    .build(), participant.getUser());
            // 최근 방문시각 저장
            participant.getUser().updateVisitedAt();
        }
    }

    /**
     * Redis와 MySQL에서 저장하고 있는 Room의 부정합성을 해결한다.
     * Redis와 MySQL 서버에서 저장하고 있는 Room들의 정합성이 다른 경우가 발생한다.
     * -> 서버가 재배포되면 Redis는 초기화되고 MySQL에는 현재 진행중인 Room들이 남게되어 둘간의 정합성이 맞지 않게된다.
     */
    private void resolveInconsistency() {
        List<Room> searchedUndeletedRooms = roomRepository.searchUndeletedRooms();
        for (Room searchedUndeletedRoom : searchedUndeletedRooms) {
            if (roomOnRedisRepository.findById(searchedUndeletedRoom.getId()).isEmpty()) {
                Room room = roomRepository.findById(searchedUndeletedRoom.getId()).orElseThrow();
                room.setDeletedAt(LocalDateTime.now());
            }
        }
    }
}