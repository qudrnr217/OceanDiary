package com.oceandiary.api.file.controller;

import com.oceandiary.api.file.dto.ImageResponse;
import com.oceandiary.api.file.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api")
public class ImageController {
    // 테스트용 (TODO: 방 생성과 합쳐진 후 삭제 예정)
    private final ImageService imageService;

    @PostMapping("/image")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ImageResponse.OnlyId> upload(@RequestPart(name = "file") MultipartFile file) {
        ImageResponse.OnlyId response = imageService.save(file);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/image/{imageId}")
    public ResponseEntity<ImageResponse.GetImage> getImage(@PathVariable Long imageId) {
        ImageResponse.GetImage response = imageService.getImage(imageId);
        return ResponseEntity.ok().body(response);
    }
}
