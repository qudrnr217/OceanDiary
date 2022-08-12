package com.oceandiary.api.room.controller;

import com.oceandiary.MvcTest;
import com.oceandiary.api.common.category.Category;
import com.oceandiary.api.file.entity.Image;
import com.oceandiary.api.room.dto.RoomRequest;
import com.oceandiary.api.room.dto.RoomResponse;
import com.oceandiary.api.room.entity.Room;
import com.oceandiary.api.room.service.RoomService;
import com.oceandiary.api.user.domain.Role;
import com.oceandiary.api.user.domain.SocialProvider;
import com.oceandiary.api.user.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Room API 문서화")
@WebMvcTest(RoomController.class)
class RoomControllerTest extends MvcTest {

    @MockBean
    private RoomService roomService;
    private User user;
    private Room room1;
    private Room room2;
    private List<Room> roomList = new ArrayList<>();

    @BeforeEach
    public void setup() {
        user = User.builder()
                .id(1L)
                .oauthId("1")
                .name("김바다")
                .birth(LocalDate.of(2002, 8,8))
                .provider(SocialProvider.NAVER)
                .role(Role.USER)
                .visitedAt(LocalDateTime.now())
                .email("oceandiary@oceandiary.com")
                .build();
        room1 = Room.builder()
                .id(1L)
                .title("방제")
                .rule("규칙")
                .category(Category.OCEAN)
                .isOpen(false)
                .pw("1234")
                .user(user)
                .maxNum(6)
                .image(Image.builder()
                        .id(1L)
                        .name("uploadedFileName")
                        .originName("originalFileName")
                        .room(room1)
                        .extension("png")
                        .width(637)
                        .height(429)
                        .size(561417L)
                        .url("url").build())
                .build();
        room2 = Room.builder()
                .id(2L)
                .title("방제2")
                .rule("규칙2")
                .category(Category.FESTIVAL)
                .isOpen(true)
                .pw(null)
                .user(user)
                .maxNum(6)
                .image(null)
                .build();
        room1.setCreatedAt(LocalDateTime.of(2022,8,8,9,00));
        room2.setCreatedAt(LocalDateTime.of(2022,8,11,11,00));
        roomList.add(room1);
        roomList.add(room2);
    }

    @Test
    @DisplayName("방_생성")
    void createRoom() throws Exception {
        RoomRequest.CreateRoom request = RoomRequest.CreateRoom.builder()
                .pw("1234")
                .title("방제")
                .isOpen(false)
                .maxNum(6)
                .rule("규칙")
                .categoryId(Category.OCEAN)
                .build();

        RoomResponse.CreateRoom response = RoomResponse.CreateRoom.builder()
                .roomId(1L)
                .participantId(1L)
                .token("openvidu-token")
                .connectionId("connection-id")
                .build();

        String jsonRequest = objectMapper.writeValueAsString(request);
        MockMultipartFile form = new MockMultipartFile("form", "form", "application/json", jsonRequest.getBytes(StandardCharsets.UTF_8));
        InputStream inputStream = new ClassPathResource("dummy/image/image.png").getInputStream();
        MockMultipartFile file = new MockMultipartFile("file", "image.png", "image/png", inputStream.readAllBytes());

        given(roomService.createRoom(any(), any(), any())).willReturn(response);
        ResultActions results = mvc.perform(multipart("/api/rooms")
                .file(form)
                .file(file)
                .contentType(MediaType.MULTIPART_MIXED)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
        );

        results.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("session-create-room",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestParts(
                                partWithName("form").description("방 생성 정보 - JSON"),
                                partWithName("file").description("이미지 파일")
                        ),
                        responseFields(
                                fieldWithPath("roomId").type(JsonFieldType.NUMBER).description("방 식별자"),
                                fieldWithPath("participantId").type(JsonFieldType.NUMBER).description("참가자 식별자"),
                                fieldWithPath("token").type(JsonFieldType.STRING).description("OpenVidu Token"),
                                fieldWithPath("connectionId").type(JsonFieldType.STRING).description("Connection 객체의 아이디")
                        )
                ));
        verify(roomService).createRoom(any(RoomRequest.CreateRoom.class), any(MultipartFile.class), any(User.class));
    }

    @Test
    @DisplayName("방_입장")
    void enterRoom() throws Exception {
        RoomRequest.EnterRoom request = RoomRequest.EnterRoom.builder()
                .pw("1234")
                .build();

        RoomResponse.EnterRoom response = RoomResponse.EnterRoom.builder()
                .participantId(1L)
                .token("openvidu-token")
                .connectionId("connection-id")
                .build();

        given(roomService.enterRoom(any(), any(), any(), any())).willReturn(response);
        ResultActions results = mvc.perform(
                        RestDocumentationRequestBuilders.post("/api/rooms/{roomId}", 1L)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                                .characterEncoding("UTF-8")
        );

        results.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("session-enter-room",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("roomId").description("방 식별자")
                        ),
                        requestFields(
                                fieldWithPath("pw").type(JsonFieldType.STRING).description("비밀번호")
                        ),
                        responseFields(
                                fieldWithPath("participantId").type(JsonFieldType.NUMBER).description("참가자 식별자"),
                                fieldWithPath("token").type(JsonFieldType.STRING).description("OpenVidu Token"),
                                fieldWithPath("connectionId").type(JsonFieldType.STRING).description("Connection 객체의 아이디")
                        )
                ));
        verify(roomService).enterRoom(any(RoomRequest.EnterRoom.class), anyLong(), any(User.class), any());
    }

    @Test
    @DisplayName("방_퇴장")
    void exitRoom() throws Exception{
        RoomResponse.OnlyId response = RoomResponse.OnlyId.builder().roomId(1L).build();
        given(roomService.exitRoom(any(), any(), any())).willReturn(response);

        ResultActions results = mvc.perform(
                RestDocumentationRequestBuilders.delete("/api/rooms/{roomId}/participants/{participantId}", 1L, 1L)
        );

        results.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("session-exit-room",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("roomId").description("방 식별자"),
                                parameterWithName("participantId").description("참가자 식별자")
                        ),
                        responseFields(
                                fieldWithPath("roomId").type(JsonFieldType.NUMBER).description("방 식별자")
                        )
                ));
        verify(roomService).exitRoom(anyLong(), anyLong(), any());
    }

    @Test
    @DisplayName("방_목록_검색")
    void searchByCondition() throws Exception {
        Page<Room> roomPage = new PageImpl<>(roomList, PageRequest.of(1, 5), roomList.size());
        Page<RoomResponse.SearchRooms> response = roomPage.map(room1 -> RoomResponse.SearchRooms.build(room1, 3));
        given(roomService.search(any(), any())).willReturn(response);

        ResultActions results = mvc.perform(get("/api/rooms"));

        results.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("search-rooms",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        relaxedResponseFields(
                                fieldWithPath("content[].roomId").type(JsonFieldType.NUMBER).description("방 식별자"),
                                fieldWithPath("content[].title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("content[].createdBy").type(JsonFieldType.NUMBER).description("유저 식별자"),
                                fieldWithPath("content[].imageId").type(JsonFieldType.NUMBER).description("이미지 식별자").optional(),
                                fieldWithPath("content[].maxNum").type(JsonFieldType.NUMBER).description("입장 제한 인원"),
                                fieldWithPath("content[].curNum").type(JsonFieldType.NUMBER).description("현재 입장 인원"),
                                fieldWithPath("content[].isOpen").type(JsonFieldType.BOOLEAN).description("방 공개 여부 - 공개 = true"),
                                fieldWithPath("totalElements").description("전체 개수"),
                                fieldWithPath("last").description("마지막 페이지인지 식별"),
                                fieldWithPath("totalPages").description("전체 페이지")
                        )
                ));
        verify(roomService).search(any(), any());
    }

    @Test
    @DisplayName("방_정보_조회")
    void roomInfo() throws Exception {
        RoomResponse.RoomInfo response = RoomResponse.RoomInfo.builder().roomId(1L).sessionId("sessionId").categoryId(Category.FESTIVAL).createdBy(1L).title("방제").rule("규칙").curNum(2).maxNum(4).imageId(1L).isOpen(true).build();
        given(roomService.getRoomInfo(any())).willReturn(response);
        ResultActions results = mvc.perform(RestDocumentationRequestBuilders.get("/api/rooms/{roomId}/info", 1L));

        results.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("room-info",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("roomId").description("방 식별자")
                        ),
                        relaxedResponseFields(
                                fieldWithPath("roomId").type(JsonFieldType.NUMBER).description("방 식별자"),
                                fieldWithPath("sessionId").type(JsonFieldType.STRING).description("세션 식별자"),
                                fieldWithPath("title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("categoryId").type(JsonFieldType.STRING).description("카테고리 식별자 - {OCEAN, LIBRARY, CAFE, FESTIVAL, HOME}"),
                                fieldWithPath("createdBy").type(JsonFieldType.NUMBER).description("유저 식별자"),
                                fieldWithPath("imageId").type(JsonFieldType.NUMBER).description("이미지 식별자").optional(),
                                fieldWithPath("maxNum").type(JsonFieldType.NUMBER).description("입장 제한 인원"),
                                fieldWithPath("curNum").type(JsonFieldType.NUMBER).description("현재 입장 인원"),
                                fieldWithPath("isOpen").type(JsonFieldType.BOOLEAN).description("방 공개 여부 - 공개 = true")
                        )
                ));
        verify(roomService).getRoomInfo(any());
    }

    @Test
    @DisplayName("방_상세정보_조회")
    void roomDetail() throws Exception {
        RoomResponse.RoomInfo roomInfo = RoomResponse.RoomInfo.builder().roomId(1L).sessionId("sessionId").categoryId(Category.FESTIVAL).createdBy(1L).title("방제").rule("규칙").curNum(2).maxNum(4).imageId(1L).isOpen(true).build();
        List<RoomResponse.RoomDetail.ParticipantInfo> participantInfoList = new ArrayList<>();
        RoomResponse.RoomDetail.ParticipantInfo participantInfo1 = RoomResponse.RoomDetail.ParticipantInfo.builder()
                .participantId(1L)
                .name("김바다")
                .enterTime(LocalDateTime.now())
                .token("token")
                .connectionId("connectionId").build();
        RoomResponse.RoomDetail.ParticipantInfo participantInfo2 = RoomResponse.RoomDetail.ParticipantInfo.builder()
                .participantId(2L)
                .name("이조개")
                .enterTime(LocalDateTime.now())
                .token("token")
                .connectionId("connectionId").build();
        participantInfoList.add(participantInfo1);
        participantInfoList.add(participantInfo2);
        RoomResponse.RoomDetail response = RoomResponse.RoomDetail.builder().roomInfo(roomInfo).participantList(participantInfoList).build();
        given(roomService.getRoomDetail(any())).willReturn(response);

        ResultActions results = mvc.perform(RestDocumentationRequestBuilders.get("/api/rooms/{roomId}/detail", 1L));

        results.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("room-detail",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("roomId").description("방 식별자")
                        ),
                        relaxedResponseFields(
                                fieldWithPath("roomInfo.roomId").type(JsonFieldType.NUMBER).description("방 식별자"),
                                fieldWithPath("roomInfo.sessionId").type(JsonFieldType.STRING).description("세션 식별자"),
                                fieldWithPath("roomInfo.title").type(JsonFieldType.STRING).description("제목"),
                                fieldWithPath("roomInfo.categoryId").type(JsonFieldType.STRING).description("카테고리 식별자 - {OCEAN, LIBRARY, CAFE, FESTIVAL, HOME}"),
                                fieldWithPath("roomInfo.createdBy").type(JsonFieldType.NUMBER).description("유저 식별자"),
                                fieldWithPath("roomInfo.imageId").type(JsonFieldType.NUMBER).description("이미지 식별자").optional(),
                                fieldWithPath("roomInfo.maxNum").type(JsonFieldType.NUMBER).description("입장 제한 인원"),
                                fieldWithPath("roomInfo.curNum").type(JsonFieldType.NUMBER).description("현재 입장 인원"),
                                fieldWithPath("roomInfo.isOpen").type(JsonFieldType.BOOLEAN).description("방 공개 여부 - 공개 = true"),
                                fieldWithPath("participantList[].participantId").type(JsonFieldType.NUMBER).description("방 식별자"),
                                fieldWithPath("participantList[].name").type(JsonFieldType.STRING).description("참가자 이름"),
                                fieldWithPath("participantList[].enterTime").type(JsonFieldType.STRING).description("입장 시간"),
                                fieldWithPath("participantList[].token").type(JsonFieldType.STRING).description("토큰"),
                                fieldWithPath("participantList[].connectionId").type(JsonFieldType.STRING).description("connection 아이디")
                        )
                ));
        verify(roomService).getRoomDetail(any());
    }

    @Test
    @DisplayName("방_정보_수정")
    void updateRoomInfo() throws Exception {
        RoomRequest.UpdateRoom request = RoomRequest.UpdateRoom.builder()
                .title("방제")
                .isOpen(false)
                .maxNum(6)
                .rule("규칙")
                .pw("1234")
                .build();

        RoomResponse.OnlyId response = RoomResponse.OnlyId.builder()
                .roomId(1L)
                .build();

        given(roomService.updateRoomInfo(any(), any(), any())).willReturn(response);
        ResultActions results = mvc.perform(patch("/api/rooms/{roomId}/info", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .characterEncoding("UTF-8")
        );

        results.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("update-room-info",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("roomId").description("방 식별자")
                        ),
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("방제목"),
                                fieldWithPath("rule").type(JsonFieldType.STRING).description("방규칙"),
                                fieldWithPath("isOpen").type(JsonFieldType.BOOLEAN).description("공개여부"),
                                fieldWithPath("pw").type(JsonFieldType.STRING).description("방비밀번호"),
                                fieldWithPath("maxNum").type(JsonFieldType.NUMBER).description("입장 제한 인원 (현재 입장한 수보다 적을 수 없음)")
                        ),
                        responseFields(
                                fieldWithPath("roomId").type(JsonFieldType.NUMBER).description("방 식별자")
                        )
                ));
        verify(roomService).updateRoomInfo(any(), any(), any());

    }
    @Test
    @DisplayName("방_이미지_수정")
    void updateRoomImage() throws Exception {
        RoomResponse.OnlyId response = RoomResponse.OnlyId.builder()
                .roomId(1L)
                .build();

        InputStream inputStream = new ClassPathResource("dummy/image/image.png").getInputStream();
        MockMultipartFile file = new MockMultipartFile("file", "image.png", "image/png", inputStream.readAllBytes());

        given(roomService.updateRoomImage(any(), any(), any())).willReturn(response);
        ResultActions results = mvc.perform(multipart("/api/rooms/{roomId}/image", 1L)
                .file(file)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .characterEncoding("UTF-8")
        );

        results.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("update-room-image",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestParts(
                                partWithName("file").description("이미지 파일").optional()
                        ),
                        responseFields(
                                fieldWithPath("roomId").type(JsonFieldType.NUMBER).description("방 식별자")
                        )
                ));
        verify(roomService).updateRoomImage(any(), any(), any());

    }

    @Test
    @DisplayName("방_강퇴")
    void dropoutParticipant() throws Exception{
        RoomResponse.OnlyId response = RoomResponse.OnlyId.builder()
                .roomId(1L)
                .build();

        given(roomService.dropoutParticipant(any(), any(), any())).willReturn(response);
        ResultActions results = mvc.perform(
                RestDocumentationRequestBuilders.post("/api/rooms/{roomId}/participants/{participantId}", 1L, 1L)
        );

        results.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("dropout-participant",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("roomId").description("방 식별자"),
                                parameterWithName("participantId").description("참가자 식별자")
                        ),

                        responseFields(
                                fieldWithPath("roomId").type(JsonFieldType.NUMBER).description("방 식별자")
                        )
                ));
        verify(roomService).dropoutParticipant(any(), any(), any());
    }

}