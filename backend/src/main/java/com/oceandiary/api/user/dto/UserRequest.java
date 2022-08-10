package com.oceandiary.api.user.dto;

import lombok.*;

public class UserRequest {
    // 테스트용 TODO: 삭제 필요
    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Login {
        private Long id;
    }
}
