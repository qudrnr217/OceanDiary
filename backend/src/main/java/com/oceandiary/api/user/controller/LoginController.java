package com.oceandiary.api.user.controller;

import com.oceandiary.api.common.utils.CookieUtils;
import com.oceandiary.api.user.request.ProviderRequest;
import com.oceandiary.api.user.response.LoginResponse;
import com.oceandiary.api.user.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.math.BigInteger;
import java.security.SecureRandom;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class LoginController {
    private final LoginService loginService;
    private static final String REFRESH_TOKEN = "refresh_token";
    private final static Integer MAX_COOKIE_TIME_S = 7 * 24 * 60 * 60;

    @GetMapping("/naver/state")
    public LoginResponse.State generateState(HttpSession session) {
        // CSRF 방지를 위한 상태 토큰 생성 코드
        // 상태 토큰은 추후 검증을 위해 세션에 저장되어야 한다.
        // 상태 토큰으로 사용할 랜덤 문자열 생성
        String state = new BigInteger(130, new SecureRandom()).toString(32);

        // 세션 또는 별도의 저장 공간에 상태 토큰을 저장
        session.setAttribute("state", state);
        log.info("session={}", session.getAttribute("state"));
        return new LoginResponse.State(state);
    }

    @PostMapping("/naver/login")
    public LoginResponse.Login loginByNaver(@RequestBody @Valid ProviderRequest.LoginRequest request, HttpSession session) {
        log.info("session={}", session.getAttribute("state"));
        return loginService.naverLogin(request, session);
    }

    @PostMapping("/kakao/login")
    public LoginResponse.Login loginByKakao(ProviderRequest.LoginRequest request) {
        log.info("code = {}", request.getCode());
        return loginService.kakaoLogin(request);
    }

    @PostMapping("/{provider}/signup")
    public ResponseEntity<LoginResponse.Join> join(@RequestBody @Valid ProviderRequest.JoinRequest request, @PathVariable String provider, HttpServletResponse response) {
        LoginResponse.JoinWithToken tokenResponse = loginService.join(request, provider);
        LoginResponse.Join finalResponse = LoginResponse.Join.builder()
                .userId(tokenResponse.getUserId())
                .accessToken(tokenResponse.getAccessToken())
                .name(tokenResponse.getName()).build();
        CookieUtils.addCookie(response, REFRESH_TOKEN, tokenResponse.getRefreshToken(), MAX_COOKIE_TIME_S);
        return ResponseEntity.ok().body(finalResponse);
    }

    @PostMapping("/user/refresh")
    public ResponseEntity<LoginResponse.Auth> refreshToken(@CookieValue(REFRESH_TOKEN) String refreshToken, HttpServletResponse response) {
        LoginResponse.Auth tokenResponse = loginService.createAccessTokenAndRefreshToken(refreshToken);
        CookieUtils.addCookie(response, REFRESH_TOKEN, tokenResponse.getRefreshToken(), MAX_COOKIE_TIME_S);
        return ResponseEntity.ok().body(tokenResponse);
    }

    @PostMapping("/user/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {
        CookieUtils.deleteCookie(response, REFRESH_TOKEN);
        return ResponseEntity.ok().build();
    }

}
