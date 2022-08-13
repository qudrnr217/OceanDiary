package com.oceandiary.api.diary.controller;


import com.oceandiary.MvcTest;
import com.oceandiary.api.common.category.Category;
import com.oceandiary.api.diary.dto.DiaryRequest;
import com.oceandiary.api.diary.dto.DiaryResponse;
import com.oceandiary.api.diary.entity.Stamp;
import com.oceandiary.api.diary.service.DiaryService;
import com.oceandiary.api.user.domain.Role;
import com.oceandiary.api.user.domain.SocialProvider;
import com.oceandiary.api.user.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Diary API 문서화")
@WebMvcTest(DiaryController.class)
public class DiaryControllerTest extends MvcTest {


    @MockBean
    private DiaryService diaryService;
    private User user;
    private Stamp stamp1;
    private Stamp stamp2;
    private List<Stamp> stampList = new ArrayList<>();



    @BeforeEach
    public void setup(){
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

        stamp1 = Stamp.builder()
                .id(1L)
                .user(user)
                .enterTime(LocalDateTime.of(2022,8,13,9,0,0))
                .exitTime(LocalDateTime.of(2022,8,13,12,0,0))
                .totalTime("3")
                .category(Category.CAFE)
                .build();

        stamp2 = Stamp.builder()
                .id(2L)
                .user(user)
                .enterTime(LocalDateTime.of(2022,8,13,15,0,0))
                .exitTime(LocalDateTime.of(2022,8,13,18,0,0))
                .totalTime("3")
                .category(Category.OCEAN)
                .build();

        stampList.add(stamp1);
        stampList.add(stamp2);
    }

    @Test
    @DisplayName("스탬프_생성")
    public void create() throws Exception{
        DiaryRequest.StampCreate request = DiaryRequest.StampCreate.builder()
                .enterTime(LocalDateTime.of(2022,8,13,9,00))
                .exitTime(LocalDateTime.of(2022,8,13,12,00))
                .category(Category.CAFE)
                .build();

        DiaryResponse.StampOnlyId response = DiaryResponse.StampOnlyId.build(stamp1);

        given(diaryService.createStamp(any(), any())).willReturn(response);
        ResultActions results = mvc.perform(RestDocumentationRequestBuilders.post("/api/diary/stamp")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .characterEncoding("UTF-8")
        );

        results.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("create-stamp",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("enterTime").type(JsonFieldType.STRING).description("방 입장 시간"),
                                fieldWithPath("exitTime").type(JsonFieldType.STRING).description("방 퇴장 시간"),
                                fieldWithPath("category").type(JsonFieldType.STRING).description("카테고리 식별자 - {OCEAN, LIBRARY, CAFE, FESTIVAL, HOME}")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("스탬프 식별자")
                        )
                ));
        verify(diaryService).createStamp(any(), any());
    }

    @Test
    @DisplayName("나의_다이어리_조회")
    void getDiary() throws Exception{
        DiaryResponse.GetDiaryContents response = DiaryResponse.GetDiaryContents.build(user, stampList);
        given(diaryService.getDiaryContents(any())).willReturn(response);
        ResultActions results = mvc.perform(RestDocumentationRequestBuilders.get("/api/diary/user/{userId}", 1L));

        results.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("get-diary",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("userId").description("유저 식별자")
                        ),
                        relaxedResponseFields(
                                fieldWithPath("userInfo.id").type(JsonFieldType.NUMBER).description("유저 식별자"),
                                fieldWithPath("userInfo.name").type(JsonFieldType.STRING).description("유저 이름"),
                                fieldWithPath("userInfo.email").type(JsonFieldType.STRING).description("유저 이메일"),
                                fieldWithPath("userInfo.birth").type(JsonFieldType.STRING).description("유저 생일"),
                                fieldWithPath("userInfo.visitedAt").type(JsonFieldType.STRING).description("마지막 방문 일자"),
                                fieldWithPath("stamps[].id").type(JsonFieldType.NUMBER).description("스탬프 식별자"),
                                fieldWithPath("stamps[].category").type(JsonFieldType.STRING).description("카테고리 식별자 - {OCEAN, LIBRARY, CAFE, FESTIVAL, HOME}"),
                                fieldWithPath("stamps[].totalTime").type(JsonFieldType.STRING).description("총 시간")

                        )
                ));
        verify(diaryService).getDiaryContents(any());

    }

    @Test
    @DisplayName("유저_정보_수정")
    void updateUserInfo() throws Exception{
        DiaryRequest.UserInfo request = DiaryRequest.UserInfo.builder()
                .name("김바다")
                .email("bada@oceandiary.com")
                .birth(LocalDate.of(2000,8,13))
                .build();

        DiaryResponse.UserInfo response = DiaryResponse.UserInfo.build(user);
        given(diaryService.updateUserInfo(any(), any(), any())).willReturn(response);
        ResultActions results = mvc.perform(RestDocumentationRequestBuilders.patch("/api/diary/user/{userId}/info", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .characterEncoding("UTF-8")
        );

        results.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("update-user-info",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("userId").description("유저 식별자")
                        ),
                        requestFields(
                                fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
                                fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                                fieldWithPath("birth").type(JsonFieldType.STRING).description("생일")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("유저 식별자"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
                                fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                                fieldWithPath("birth").type(JsonFieldType.STRING).description("생일"),
                                fieldWithPath("visitedAt").type(JsonFieldType.STRING).description("마지막 방문일")
                        )
                ));
        verify(diaryService).updateUserInfo(any(), any(), any());
    }

    @Test
    @DisplayName("유저 탈퇴")
    void deleteUserInfo() throws Exception{
        DiaryResponse.UserOnlyId response = DiaryResponse.UserOnlyId.build(user);

        given(diaryService.deleteUserInfo(any(), any())).willReturn(response);
        ResultActions results = mvc.perform(RestDocumentationRequestBuilders.patch("/api/diary/user/{userId}", 1L));

        results.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("delete-user-info",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("userId").description("유저 식별자")
                        ),
                        relaxedResponseFields(
                                fieldWithPath("userId").description("유저 식별자")
                        )
                ));
        verify(diaryService).deleteUserInfo(any(), any());

    }

}

