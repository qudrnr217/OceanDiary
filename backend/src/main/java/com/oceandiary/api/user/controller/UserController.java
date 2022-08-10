package com.oceandiary.api.user.controller;

import com.oceandiary.api.user.dto.UserRequest;
import com.oceandiary.api.user.dto.UserResponse;
import com.oceandiary.api.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    // 테스트용 TODO: 삭제 필요
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<UserResponse.Login> login(@RequestBody UserRequest.Login request, HttpServletResponse response) {
        UserResponse.Login loginResponse = userService.login(request);
        return ResponseEntity.ok().body(loginResponse);
    }
}
