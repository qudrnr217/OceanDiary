package com.oceandiary.api.user.controller;

import com.google.gson.Gson;
import com.oceandiary.MvcTest;
import com.oceandiary.api.user.request.NaverLoginJoinRequest;
import com.oceandiary.api.user.request.NaverLoginRequest;
import com.oceandiary.api.user.response.NaverLoginJoinResponse;
import com.oceandiary.api.user.response.NaverLoginResponse;
import com.oceandiary.api.user.service.NaverLoginService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(NaverLoginController.class)
class NaverLoginControllerTest extends MvcTest {
    @MockBean
    private NaverLoginService naverLoginService;

    @Test
    public void 상태토큰_생성() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/naver/state"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andDo(document("naver-login-state",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("state").description("상태 코드")
                        )
                ));
    }

    @Test
    void 네이버로그인_최초사용자_로그인() throws Exception {
        NaverLoginRequest request = new NaverLoginRequest();
        NaverLoginResponse response = new NaverLoginResponse();
        request.setCode("code");
        response.setExist(false);
        response.setOauthId("oauthid");
        MockHttpSession mockHttpSession = new MockHttpSession();
        given(naverLoginService.login(request, mockHttpSession)).
                willReturn(response);

        Gson gson = new Gson();
        String jsonRequest = gson.toJson(request);
        mvc.perform(post("/api/naver/login").content(jsonRequest).contentType(MediaType.APPLICATION_JSON).with(csrf()).session(mockHttpSession))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isExist").value(false))
                .andExpect(jsonPath("$.oauthId").value("oauthid"))
                .andDo(document("naver-login-first-user",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("code").description("인증 코드")
                        ),
                        responseFields(
                                fieldWithPath("accessToken").description("네이버 access-token").ignored(),
                                fieldWithPath("userId").description("유저 식별 아이디").ignored(),
                                fieldWithPath("name").description("유저 이름").ignored(),
                                fieldWithPath("isExist").description("존재하는 회원인지"),
                                fieldWithPath("oauthId").description("네이버 고유 아이디")
                        )
                ));
        verify(naverLoginService).login(request, mockHttpSession);
    }

    @Test
    void 네이버로그인_회원가입된_사용자_로그인() throws Exception {
        NaverLoginRequest request = new NaverLoginRequest();
        NaverLoginResponse response = new NaverLoginResponse();
        request.setCode("statecode");
        response.setExist(true);
        response.setAccessToken("access-token");
        response.setUserId(1L);
        response.setName("황재완");
        MockHttpSession mockHttpSession = new MockHttpSession();
        given(naverLoginService.login(request, mockHttpSession)).
                willReturn(response);

        Gson gson = new Gson();
        String jsonRequest = gson.toJson(request);
        mvc.perform(post("/api/naver/login").content(jsonRequest).contentType(MediaType.APPLICATION_JSON).with(csrf()).session(mockHttpSession))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isExist").value(true))
                .andExpect(jsonPath("$.accessToken").value("access-token"))
                .andExpect(jsonPath("$.userId").value(1L))
                .andExpect(jsonPath("$.name").value("황재완"))
                .andDo(document("naver-login-duplicate-user",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("code").description("인증 코드")
                        ),
                        responseFields(
                                fieldWithPath("accessToken").description("네이버 access-token"),
                                fieldWithPath("userId").description("유저 식별 아이디"),
                                fieldWithPath("name").description("유저 이름"),
                                fieldWithPath("isExist").description("존재하는 회원인지"),
                                fieldWithPath("oauthId").description("네이버 고유 아이디").ignored()
                        )
                ));

        verify(naverLoginService).login(request, mockHttpSession);
    }

    @Test
    void 네이버로그인_회원가입() throws Exception {
        NaverLoginJoinRequest request = new NaverLoginJoinRequest();
        NaverLoginJoinResponse response = new NaverLoginJoinResponse();
        request.setEmail("jaeawn9074@gmail.com");
        request.setName("황재완");
        request.setOauthId("oauthid");
        request.setBirth(LocalDate.of(2022, 07, 10));
        response.setAccessToken("access-token");
        response.setUserId(1L);
        response.setName("황재완");
        given(naverLoginService.join(request)).
                willReturn(response);

        Gson gson = new Gson();
        String jsonRequest = gson.toJson(request);
        jsonRequest = jsonRequest.replace("{\"year\":2022,\"month\":7,\"day\":10}", "\"2022-07-10\"");

        mvc.perform(post("/api/naver/signup").content(jsonRequest).contentType(MediaType.APPLICATION_JSON).with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value("access-token"))
                .andExpect(jsonPath("$.userId").value(1L))
                .andExpect(jsonPath("$.name").value("황재완"))
                .andDo(document("naver-login-signup",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("email").description("유저 이메일"),
                                fieldWithPath("name").description("유저 이름"),
                                fieldWithPath("birth").description("유저 생년월일"),
                                fieldWithPath("oauthId").description("네이버 고유 아이디")
                        ),
                        responseFields(
                                fieldWithPath("accessToken").description("네이버 access-token"),
                                fieldWithPath("userId").description("유저 식별 아이디"),
                                fieldWithPath("name").description("유저 이름")
                        )
                ));

        verify(naverLoginService).join(request);

    }
}