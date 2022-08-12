package com.oceandiary.api.diary.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.oceandiary.api.common.category.Category;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DiaryRequest {
    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class StampCreate {
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime enterTime;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime exitTime;
        private Category category;
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class UserInfo{
        private Long id;
        private String name;
        private String email;
        private LocalDate birth;
    }
}
