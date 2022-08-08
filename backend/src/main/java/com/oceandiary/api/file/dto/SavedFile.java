package com.oceandiary.api.file.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SavedFile {
    private final String originName;
    private final String name;
    private final String extension;
    private final Long size;
    private final String publicUrl;

    private final Integer width;
    private final Integer height;

    private final boolean isImage;

    public static SavedFile create(String s3FileName, String extension, String originName, long fileSize, boolean isImage, String publicUrl, Integer width, Integer height) {
        return SavedFile.builder()
                .name(s3FileName)
                .extension(extension)
                .originName(originName)
                .size(fileSize)
                .isImage(isImage)
                .publicUrl(publicUrl)
                .width(width)
                .height(height)
                .build();

    }
}
