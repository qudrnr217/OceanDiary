package com.oceandiary.api.user.controller;


import com.oceandiary.api.user.request.NaverRequest;
import com.oceandiary.api.user.response.NaverResponse;
import com.oceandiary.api.user.service.KakaoLoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/kakao")
public class KakaoLoginController {

    private final KakaoLoginService kakaoLoginService;

    @PostMapping(value = "/login")
    public NaverResponse.LoginResponse loginByKakao(NaverRequest.LoginRequest request) {
        log.info("code = {}", request.getCode());
        return kakaoLoginService.login(request);
    }

    @PostMapping("/signup")
    public NaverResponse.JoinResponse joinByKakao(@RequestBody @Valid NaverRequest.JoinRequest request) {
        return kakaoLoginService.join(request);
    }
}
