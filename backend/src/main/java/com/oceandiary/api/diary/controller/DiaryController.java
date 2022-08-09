package com.oceandiary.api.diary.controller;


import com.oceandiary.api.diary.dto.DiaryRequest;
import com.oceandiary.api.diary.dto.DiaryResponse;
import com.oceandiary.api.diary.service.StampService;
import com.oceandiary.api.user.security.userdetails.CurrentUser;
import com.oceandiary.api.user.security.userdetails.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/diary")
public class DiaryController {
    private final StampService stampService;

    @PostMapping("/stamp")
    public ResponseEntity<DiaryResponse.StampOnlyId> create(@RequestBody DiaryRequest.StampCreate request, @CurrentUser CustomUserDetails customUserDetails) {
        DiaryResponse.StampOnlyId response = stampService.create(request, customUserDetails.getUser());
        return ResponseEntity.ok().body(response);
    }

    // TODO: user 정보 함께 출력하는 Response 형태로 변경 필요
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<DiaryResponse.GetStamp>> getList(@PathVariable("userId") Long userId) {
        List<DiaryResponse.GetStamp> response = stampService.getList(userId);
        return ResponseEntity.ok().body(response);
    }
}
