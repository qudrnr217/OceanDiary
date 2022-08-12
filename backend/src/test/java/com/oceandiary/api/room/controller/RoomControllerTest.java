package com.oceandiary.api.room.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.oceandiary.MvcTest;
import com.oceandiary.api.common.category.Category;
import com.oceandiary.api.room.request.RoomRequest;
import com.oceandiary.api.room.response.RoomResponse;
import com.oceandiary.api.room.service.RoomService;
import com.oceandiary.api.user.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.web.multipart.MultipartFile;

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

@WebMvcTest(RoomController.class)
class RoomControllerTest extends MvcTest {

    @MockBean
    private RoomService roomService;

    @DisplayName("방_생성")
    void createRoom() throws Exception {
        RoomRequest.CreateRoom request = RoomRequest.CreateRoom.builder()
                .pw("1234")
                .title("테스트방")
                .isOpen(false)
                .maxNum(5)
                .rule("규칙")
                .categoryId(Category.LIBRARY)
                .build();

        RoomResponse.CreateRoom response = RoomResponse.CreateRoom.builder()
                .roomId(1L)
                .participantId(1L)
                .token("openvidu-token")
                .connectionId("connection-id")
                .build();

        MockMultipartFile multipartFile = new MockMultipartFile("file", "image.png", "image/png", "<<png data>>".getBytes());
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonRequest = objectMapper.writeValueAsString(request);
        MockMultipartFile json = new MockMultipartFile("form", "form", "application/json", jsonRequest.getBytes());
        given(roomService.createRoom(any(RoomRequest.CreateRoom.class), any(MultipartFile.class), any(User.class)))
                .willReturn(response);
        mvc.perform(multipart("/api/rooms")
                        .file(json)
                        .file(multipartFile)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())
                .andDo(document("session-create-room",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestParts(
                                partWithName("form").description("방 생성 정보"),
                                partWithName("file").description("이미지 파일")
                        ),
                        responseFields(
                                fieldWithPath("roomId").description("방 아이디").ignored(),
                                fieldWithPath("participantId").description("참가자 식별 아이디").ignored(),
                                fieldWithPath("token").description("OpenVidu Token").ignored(),
                                fieldWithPath("connectionId").description("Connection 객체의 아이디")
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

        given(roomService.enterRoom(any(RoomRequest.EnterRoom.class), anyLong(), any(User.class), any()))
                .willReturn(response);

        Long roomId = 1L;
        String jsonRequest = new Gson().toJson(request);
        mvc.perform(
                        RestDocumentationRequestBuilders.post("/api/rooms/{roomId}", roomId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(jsonRequest))
                .andExpect(status().isOk())
                .andDo(document("session-enter-room",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("roomId").description("방 식별자")
                        ),
                        requestFields(
                                fieldWithPath("pw").description("비밀번호")
                        ),
                        responseFields(
                                fieldWithPath("participantId").description("참가자 식별 아이디").ignored(),
                                fieldWithPath("token").description("OpenVidu Token").ignored(),
                                fieldWithPath("connectionId").description("Connection 객체의 아이디")
                        )
                ));
        verify(roomService).enterRoom(any(RoomRequest.EnterRoom.class), anyLong(), any(User.class), any());
    }

    @Test
    void exitRoom() {
    }

    @Test
    void search() {
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