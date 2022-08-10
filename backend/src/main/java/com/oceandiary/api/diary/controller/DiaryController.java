package com.oceandiary.api.diary.controller;


import com.oceandiary.api.diary.dto.DiaryRequest;
import com.oceandiary.api.diary.dto.DiaryResponse;
import com.oceandiary.api.diary.service.StampService;
import com.oceandiary.api.user.dto.UserInfoResponse;
import com.oceandiary.api.user.security.userdetails.CurrentUser;
import com.oceandiary.api.user.security.userdetails.CustomUserDetails;
import com.oceandiary.api.user.service.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/diary")
public class DiaryController {
    private final StampService stampService;

    private final UserInfoService userInfoService;

    @PostMapping("/stamp")
    public ResponseEntity<DiaryResponse.StampOnlyId> create(@RequestBody DiaryRequest.StampCreate request, @CurrentUser CustomUserDetails customUserDetails) {
        DiaryResponse.StampOnlyId response = stampService.create(request, customUserDetails.getUser());
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<UserInfoResponse> getList(@PathVariable("userId") Long userId) {
        UserInfoResponse userInfoResponse = userInfoService.getUserInfo(userId);
        return ResponseEntity.ok().body(userInfoResponse);
    }
}
