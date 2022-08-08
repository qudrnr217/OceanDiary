package com.oceandiary.api.room.service;

import com.oceandiary.api.common.exception.BusinessException;
import com.oceandiary.api.room.dto.RoomSearchCondition;
import com.oceandiary.api.room.entity.Participant;
import com.oceandiary.api.room.entity.ParticipantOnRedis;
import com.oceandiary.api.room.entity.Room;
import com.oceandiary.api.room.entity.RoomOnRedis;
import com.oceandiary.api.room.exception.PasswordNotValidException;
import com.oceandiary.api.room.repository.ParticipantOnRedisRepository;
import com.oceandiary.api.room.repository.ParticipantRepository;
import com.oceandiary.api.room.repository.RoomOnRedisRepository;
import com.oceandiary.api.room.repository.RoomRepository;
import com.oceandiary.api.room.request.RoomRequest;
import com.oceandiary.api.room.response.RoomResponse;
import com.oceandiary.api.user.entity.User;
import io.openvidu.java.client.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
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
    private final RoomRepository roomRepository;
    private final RoomOnRedisRepository roomOnRedisRepository;
    private final ParticipantRepository participantRepository;
    private final ParticipantOnRedisRepository participantOnRedisRepository;

    @Value("${openvidu.secret}")
    private String OPENVIDU_SECRET;

    public RoomService(@Value("${openvidu.secret}") String secret, @Value("${openvidu.url}") String openviduUrl, RoomRepository roomRepository, RoomOnRedisRepository roomOnRedisRepository, ParticipantRepository participantRepository, ParticipantOnRedisRepository participantOnRedisRepository) {
        this.SECRET = secret;
        this.OPENVIDU_URL = openviduUrl;
        this.roomRepository = roomRepository;
        this.roomOnRedisRepository = roomOnRedisRepository;
        this.participantRepository = participantRepository;
        this.participantOnRedisRepository = participantOnRedisRepository;
        this.openVidu = new OpenVidu(OPENVIDU_URL, SECRET);
    }

    public RoomResponse.CreateRoom createRoom(RoomRequest.CreateRoom request, User user) {

        String serverData = "{\"userId\": \"" + user.getId() + "\"," +
                "\"name\": \"" + user.getName() + "\"}";
        ConnectionProperties connectionProperties =
                new ConnectionProperties.Builder().type(ConnectionType.WEBRTC).data(serverData).role(OpenViduRole.MODERATOR).build();

        log.info("openvidu 접속 테스트 완료");

        if (roomOnRedisRepository.findByCreatedBy(user.getId()).isPresent()) {
            throw new BusinessException("이미 방을 생성한 유저가 다시 생성함");
        }

        try {
            Room room = Room.builder()
                    .category(request.getCategoryId())
                    .user(user)
                    .title(request.getTitle())
                    .rule(request.getRule())
                    .maxNum(request.getMaxNum())
                    .isOpen(request.getIsOpen())
                    .pw(request.getPw())
                    .build();
            Room newRoom = roomRepository.save(room);
            log.info("생성된 방: {}", newRoom);

            // Create a new OpenVidu Session
            Session session = this.openVidu.createSession();
            // Generate a new Connection with the recently created connectionProperties
            String token = session.createConnection(connectionProperties).getToken();

            RoomOnRedis newRoomOnRedis = RoomOnRedis.builder().id(newRoom.getId()).sessionId(session.getSessionId()).createdBy(user.getId()).build();
            roomOnRedisRepository.save(newRoomOnRedis);
            ParticipantOnRedis participantOnRedis = ParticipantOnRedis.builder().id(newRoom.getId()).participants(new ConcurrentHashMap<>()).build();
            ParticipantOnRedis newParticipantOnRedis = participantOnRedisRepository.save(participantOnRedis);

            Participant newParticipant = Participant.builder()
                    .room(newRoom)
                    .user(user)
                    .name(user.getName())
                    .enterDate(LocalDateTime.now())
                    .build();

            newParticipant = participantRepository.save(newParticipant);

            newParticipantOnRedis.addParticipant(newParticipant.getId(), token);
            participantOnRedisRepository.save(newParticipantOnRedis);

            return RoomResponse.CreateRoom.builder()
                    .roomId(newRoom.getId())
                    .participantId(newParticipant.getId())
                    .token(token)
                    .build();

        } catch (Exception e) {
            throw new BusinessException("세션 생성중 예외 발생");
        }
    }

    public RoomResponse.EnterRoom enterRoom(RoomRequest.EnterRoom request, Long roomId, User user) {
        // Session already exists

        String serverData = "{\"userId\": \"" + user.getId() + "\"," +
                "\"name\": \"" + user.getName() + "\"}";
        ConnectionProperties connectionProperties =
                new ConnectionProperties.Builder().type(ConnectionType.WEBRTC).data(serverData).role(OpenViduRole.PUBLISHER).build();

        try {
            // Generate a new Connection with the recently created connectionProperties
            String sessionId = roomOnRedisRepository.findById(roomId).orElseThrow().getSessionId();
            Session session = getSession(sessionId);

            String token = session.createConnection(connectionProperties).getToken();
            Room room = roomRepository.findById(roomId).orElseThrow();

            // 비밀번호 설정된 방인데 비밀번호가 틀린경우
            if (!room.getIsOpen() && !room.getPw().equals(request.getPw())){
                throw new PasswordNotValidException();
            }

            Participant newParticipant = Participant.builder()
                    .room(room)
                    .user(user)
                    .name(user.getName())
                    .enterDate(LocalDateTime.now())
                    .build();

            newParticipant = participantRepository.save(newParticipant);

            ParticipantOnRedis participantOnRedis = participantOnRedisRepository.findById(roomId).orElseThrow();
            participantOnRedis.addParticipant(newParticipant.getId(), token);
            participantOnRedisRepository.save(participantOnRedis);

            return RoomResponse.EnterRoom.builder()
                    .participantId(newParticipant.getId())
                    .token(token)
                    .build();

        } catch (OpenViduJavaClientException e) {
            // If internal error generate an error message and return it to client
            throw new BusinessException(e.getMessage());
        } catch (OpenViduHttpException e) {
            if (404 == e.getStatus()) {
                // Invalid sessionId (user left unexpectedly). Session object is not valid
                // anymore. Clean collections and continue as new session
                roomOnRedisRepository.deleteById(roomId);
                roomOnRedisRepository.deleteById(roomId);
                Room room = roomRepository.findById(roomId).orElseThrow();
                room.setDeletedAt(LocalDateTime.now());
            }
            throw new BusinessException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void exitRoom(Long roomId, Long participantId, User user) {

        try {
            RoomOnRedis roomOnRedis = roomOnRedisRepository.findById(roomId).orElseThrow();
            ParticipantOnRedis participantOnRedis = participantOnRedisRepository.findById(roomId).orElseThrow();

            Session session = getSession(roomOnRedis.getSessionId());

            if (user.getId() == roomOnRedis.getCreatedBy()) {  // 방장이 나갈경우
                session.close();
                for (Long pid  : participantOnRedis.getParticipants().keySet()) {
                    Participant participant = participantRepository.findById(pid).orElseThrow();
                    participant.addExitDate();
                }
                participantOnRedis.getParticipants().clear();
            } else {
                Participant participant = participantRepository.findById(participantId).orElseThrow();
                participant.addExitDate();
            }

            participantOnRedis.removeParticipant(participantId);

            if (participantOnRedis.getParticipants().isEmpty()) {  // 방에 더이상 참가자가 없을경우
                participantOnRedisRepository.deleteById(roomId);
                roomOnRedisRepository.deleteById(roomId);
                Room room = roomRepository.findById(roomId).orElseThrow();
                room.setDeletedAt(LocalDateTime.now());
                log.info("방 세션 종료");
            } else {  // 방에 아직 참가자가 있을경우
                participantOnRedisRepository.save(participantOnRedis);
            }

        } catch (OpenViduJavaClientException e) {
            // If internal error generate an error message and return it to client
            throw new BusinessException(e.getMessage());
        } catch (OpenViduHttpException e) {
            if (404 == e.getStatus()) {
                // Invalid sessionId (user left unexpectedly). Session object is not valid
                // anymore. Clean collections and continue as new session
                roomOnRedisRepository.deleteById(roomId);
                roomOnRedisRepository.deleteById(roomId);
                Room room = roomRepository.findById(roomId).orElseThrow();
                room.setDeletedAt(LocalDateTime.now());
            }
            throw new BusinessException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Page<RoomResponse.SearchRooms> search(RoomSearchCondition condition, Pageable pageable) {
        Page<RoomResponse.SearchRooms> page = roomRepository.search(condition, pageable);
        for (RoomResponse.SearchRooms searchedRoom : page.getContent()) {
            Integer searchedRoomId = participantOnRedisRepository.findById(searchedRoom.getRoomId()).orElseThrow().getParticipants().size();
            searchedRoom.setCurNum(searchedRoomId);
        }
        return page;
    }

    public Session getSession(String sessionId) {
        List<Session> activeSessions = this.openVidu.getActiveSessions();
        return activeSessions.stream().filter(s -> s.getSessionId().equals(sessionId)).findFirst().orElseThrow();
    }
}



