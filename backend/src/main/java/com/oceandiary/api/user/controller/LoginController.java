package com.oceandiary.api.user.controller;

import com.oceandiary.api.user.request.JoinRequest;
import com.oceandiary.api.user.request.LoginRequest;
import com.oceandiary.api.user.response.JoinResponse;
import com.oceandiary.api.user.response.LoginResponse;
import com.oceandiary.api.user.response.StateResponse;
import com.oceandiary.api.user.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/naver/state")
    public StateResponse generateState(HttpSession session) {
        // CSRF 방지를 위한 상태 토큰 생성 코드
        // 상태 토큰은 추후 검증을 위해 세션에 저장되어야 한다.
        // 상태 토큰으로 사용할 랜덤 문자열 생성
        String state = new BigInteger(130, new SecureRandom()).toString(32);
        ;
        // 세션 또는 별도의 저장 공간에 상태 토큰을 저장
        session.setAttribute("state", state);
        log.info("session={}", session.getAttribute("state"));
        return new StateResponse(state);
    }

    @PostMapping("/naver/login")
    public LoginResponse loginByNaver(@RequestBody @Valid LoginRequest request, HttpSession session) {
        log.info("session={}", session.getAttribute("state"));
        return loginService.naverLogin(request, session);
    }

    @PostMapping("/kakao/login")
    public LoginResponse loginByKakao(LoginRequest request) {
        log.info("code = {}", request.getCode());
        return loginService.kakaoLogin(request);
    }

    @PostMapping("/{provider}/signup")
    public JoinResponse join(@RequestBody @Valid JoinRequest request, @PathVariable String provider) {
        return loginService.join(request, provider);
    }
}
