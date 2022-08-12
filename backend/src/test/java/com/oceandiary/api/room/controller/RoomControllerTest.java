package com.oceandiary.api.room.controller;

import com.oceandiary.MvcTest;
import com.oceandiary.api.common.category.Category;
import com.oceandiary.api.file.entity.Image;
import com.oceandiary.api.room.entity.Room;
import com.oceandiary.api.room.dto.RoomRequest;
import com.oceandiary.api.room.dto.RoomResponse;
import com.oceandiary.api.room.service.RoomService;
import com.oceandiary.api.user.domain.Role;
import com.oceandiary.api.user.domain.SocialProvider;
import com.oceandiary.api.user.entity.User;
import com.oceandiary.api.user.security.userdetails.CustomUserDetails;
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
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Room API 문서화")
@WebMvcTest(RoomController.class)
class RoomControllerTest extends MvcTest {

    @MockBean
    private RoomService roomService;
    private User user;
    private Image image;
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
        image = Image.builder()
                .id(1L)
                .name("uploadedFileName")
                .originName("originalFileName")
                .room(room1)
                .extension("png")
                .width(637)
                .height(429)
                .size(561417L)
                .url("url")
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
                .andDo(document("session-create-room",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestParts(
                                partWithName("form").description("방 생성 정보 - JSON"),
                                partWithName("file").description("이미지 파일")
                        ),
                        responseFields(
                                fieldWithPath("roomId").type(JsonFieldType.NUMBER).description("방 식별자").ignored(),
                                fieldWithPath("participantId").type(JsonFieldType.NUMBER).description("참가자 식별자").ignored(),
                                fieldWithPath("token").type(JsonFieldType.STRING).description("OpenVidu Token").ignored(),
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
                                fieldWithPath("participantId").type(JsonFieldType.NUMBER).description("참가자 식별자").ignored(),
                                fieldWithPath("token").type(JsonFieldType.STRING).description("OpenVidu Token").ignored(),
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
    void searchByCondition() {
        RoomRequest.RoomSearchCondition  request = RoomRequest.RoomSearchCondition.builder().category(Category.OCEAN).title("방제").lastRoomId(1L).build();
        Page<Room> roomPage = new PageImpl<>(roomList, PageRequest.of(1, 5), roomList.size());
        Page<RoomResponse.SearchRooms> response = roomPage.map(room1 -> RoomResponse.SearchRooms.builder()..build())
        given(roomService.exitRoom(any(), any(), any())).willReturn(response);

        ResultActions results = mvc.perform(
                RestDocumentationRequestBuilders.delete("/api/rooms/{roomId}/participants/{participantId}", 1L, 1L)
        );

        results.andExpect(status().isOk())
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
    void roomInfo() {
    }

    @Test
    void roomDetail() {
    }

    @Test
    void updateRoomInfo() {
    }

    @Test
    void dropoutParticipant() {
    }

}