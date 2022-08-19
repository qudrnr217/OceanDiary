package com.oceandiary.api.file.dto;

import com.oceandiary.api.file.entity.Image;
import lombok.*;

public class ImageResponse {
    // TODO: 방 생성과 연결 후 삭제 예정
    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class OnlyId {
        private Long id;

        public static ImageResponse.OnlyId build(Image image) {
            return ImageResponse.OnlyId.builder()
                    .id(image.getId())
                    .build();
        }

    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class GetImage {
        private Long id;
        private String originName;
        private String name;
        private String extension;
        private Long size;
        private int width;
        private int height;
        private String base64Str;
        public static ImageResponse.GetImage build(Image image, String base64Str) {
            return GetImage.builder()
                    .id(image.getId())
                    .name(image.getName())
                    .extension(image.getExtension())
                    .originName(image.getOriginName())
                    .size(image.getSize())
                    .width(image.getWidth())
                    .height(image.getHeight())
                    .base64Str(base64Str)
                    .build();
        }

    }

}
