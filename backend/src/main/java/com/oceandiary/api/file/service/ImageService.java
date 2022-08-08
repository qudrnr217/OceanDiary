package com.oceandiary.api.file.service;

import com.oceandiary.api.file.dto.ImageResponse;
import com.oceandiary.api.file.entity.Image;
import com.oceandiary.api.file.exception.FileNotFoundException;
import com.oceandiary.api.file.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ImageService {
    // TODO: 방 생성과 연결 후 삭제 예정
    private final ImageRepository imageRepository;
    private final S3Service s3Service;

    public ImageResponse.OnlyId save(MultipartFile file) {
        return ImageResponse.OnlyId.build(imageRepository.save(Image.create(s3Service.upload(file))));
    }

    public ImageResponse.GetImage getImage(Long imageId) {
        return ImageResponse.GetImage.build(imageRepository.findById(imageId).orElseThrow(FileNotFoundException::new));
    }
}
