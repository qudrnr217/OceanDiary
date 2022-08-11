package com.oceandiary.api.user.controller;

import com.google.gson.Gson;
import com.oceandiary.MvcTest;
import com.oceandiary.api.user.request.ProviderRequest;
import com.oceandiary.api.user.response.LoginResponse;
import com.oceandiary.api.user.service.LoginService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LoginController.class)
@WebAppConfiguration
class LoginControllerTest extends MvcTest {
    @MockBean
    private LoginService loginService;

    @Test
    @DisplayName("상태토큰_생성")
    public void generateState() throws Exception {
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
    @DisplayName("네이버로그인_최초사용자_로그인")
    public void firstLoginByNaver() throws Exception {
        ProviderRequest.LoginRequest request = new ProviderRequest.LoginRequest();
        LoginResponse.Login response = LoginResponse.Login.builder()
                .isExist(false)
                .oauthId("oauthid")
                .build();
        request.setCode("code");
        MockHttpSession mockHttpSession = new MockHttpSession();
        given(loginService.naverLogin(request, mockHttpSession)).
                willReturn(response);

        Gson gson = new Gson();
        String jsonRequest = gson.toJson(request);
        mvc.perform(post("/api/naver/login").content(jsonRequest).contentType(MediaType.APPLICATION_JSON).session(mockHttpSession))
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
        verify(loginService).naverLogin(request, mockHttpSession);
    }

    @Test
    @DisplayName("네이버로그인_회원가입된_사용자_로그인")
    void loginByNaverWithPresentUser() throws Exception {
        ProviderRequest.LoginRequest request = new ProviderRequest.LoginRequest();
        LoginResponse.Login response = LoginResponse.Login.builder()
                .isExist(true)
                .accessToken("access-token")
                .userId(1L)
                .name("황재완")
                .build();
        request.setCode("statecode");
        MockHttpSession mockHttpSession = new MockHttpSession();
        given(loginService.naverLogin(request, mockHttpSession)).
                willReturn(response);

        Gson gson = new Gson();
        String jsonRequest = gson.toJson(request);
        mvc.perform(post("/api/naver/login").content(jsonRequest).contentType(MediaType.APPLICATION_JSON).session(mockHttpSession))
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

        verify(loginService).naverLogin(request, mockHttpSession);
    }

    @Test
    @DisplayName("네이버로그인_회원가입")
    void joinByNaver() throws Exception {
        ProviderRequest.JoinRequest request = new ProviderRequest.JoinRequest();
        LoginResponse.Join response = LoginResponse.Join.builder()
                .accessToken("access-token")
                .userId(1L)
                .name("황재완")
                .build();
        request.setEmail("jaeawn9074@gmail.com");
        request.setName("황재완");
        request.setOauthId("oauthid");
        request.setBirth(LocalDate.of(2022, 07, 10));

        given(loginService.join(request, "naver")).
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
                                fieldWithPath("oauthId").description("고유 아이디")
                        ),
                        responseFields(
                                fieldWithPath("accessToken").description("네이버 access-token"),
                                fieldWithPath("userId").description("유저 식별 아이디"),
                                fieldWithPath("name").description("네이버 유저 이름")
                        )
                ));

        verify(loginService).join(request, "naver");

    }

    @Test
    @DisplayName("카카오로그인_최초사용자_로그인")
    public void firstLoginByKakao() throws Exception {
        ProviderRequest.LoginRequest request = new ProviderRequest.LoginRequest();
        LoginResponse.Login response = LoginResponse.Login.builder()
                .isExist(false)
                .oauthId("oauthid")
                .build();

        request.setCode("code");
        given(loginService.kakaoLogin(request)).willReturn(response);

        mvc.perform(post("/api/kakao/login").contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE).param("code", request.getCode()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isExist").value(false))
                .andExpect(jsonPath("$.oauthId").value("oauthid"))
                .andDo(document("kakao-login-first-user",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestParameters(
                            parameterWithName("code").description("code")
                        ),
                        responseFields(
                                fieldWithPath("accessToken").description("카카오 access-token").ignored(),
                                fieldWithPath("userId").description("유저 식별 아이디").ignored(),
                                fieldWithPath("name").description("유저 이름").ignored(),
                                fieldWithPath("isExist").description("존재하는 회원인지"),
                                fieldWithPath("oauthId").description("카카오 고유 아이디")
                        )
                ));
        verify(loginService).kakaoLogin(request);
    }

    @Test
    @DisplayName("카카오로그인_회원가입된_사용자_로그인")
    void loginByKakaoWithPresentUser() throws Exception {
        ProviderRequest.LoginRequest request = new ProviderRequest.LoginRequest();
        LoginResponse.Login response = LoginResponse.Login.builder()
                .isExist(true)
                .accessToken("access-token")
                .userId(1L)
                .name("김싸피")
                .build();
        request.setCode("code");
        given(loginService.kakaoLogin(request)).willReturn(response);

        mvc.perform(post("/api/kakao/login").param("code", request.getCode()).contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isExist").value(true))
                .andExpect(jsonPath("$.accessToken").value("access-token"))
                .andExpect(jsonPath("$.userId").value(1L))
                .andExpect(jsonPath("$.name").value("김싸피"))
                .andDo(document("kakao-login-duplicate-user",
                        preprocessResponse(prettyPrint()),
                        requestParameters(
                                parameterWithName("code").description("code")
                        ),
                        responseFields(
                                fieldWithPath("accessToken").description("카카오 access-token"),
                                fieldWithPath("userId").description("유저 식별 아이디"),
                                fieldWithPath("name").description("유저 이름"),
                                fieldWithPath("isExist").description("존재하는 회원인지"),
                                fieldWithPath("oauthId").description("카카오 고유 아이디").ignored()
                        )
                ));

        verify(loginService).kakaoLogin(request);
    }

    @Test
    @DisplayName("카카오로그인_회원가입")
    void joinByKakao() throws Exception {
        ProviderRequest.JoinRequest request = new ProviderRequest.JoinRequest();
        LoginResponse.Join response = LoginResponse.Join.builder()
                .accessToken("access-token")
                .userId(1L)
                .name("김싸피")
                .build();
        request.setEmail("ssafy@gmail.com");
        request.setName("김싸피");
        request.setOauthId("oauthid");
        request.setBirth(LocalDate.of(2022, 07, 10));

        given(loginService.join(request, "kakao")).
                willReturn(response);

        Gson gson = new Gson();
        String jsonRequest = gson.toJson(request);

        jsonRequest = jsonRequest.replace("{\"year\":2022,\"month\":7,\"day\":10}", "\"2022-07-10\"");

        mvc.perform(post("/api/kakao/signup").content(jsonRequest).contentType(MediaType.APPLICATION_JSON).with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value("access-token"))
                .andExpect(jsonPath("$.userId").value(1L))
                .andExpect(jsonPath("$.name").value("김싸피"))
                .andDo(document("kakao-login-signup",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("email").description("유저 이메일"),
                                fieldWithPath("name").description("유저 이름"),
                                fieldWithPath("birth").description("유저 생년월일"),
                                fieldWithPath("oauthId").description("고유 아이디")
                        ),
                        responseFields(
                                fieldWithPath("accessToken").description("카카오 access-token"),
                                fieldWithPath("userId").description("유저 식별 아이디"),
                                fieldWithPath("name").description("카카오 유저 이름")
                        )
                ));

        verify(loginService).join(request, "kakao");

    }

}