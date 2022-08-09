package com.oceandiary.api.user.dto;

import com.oceandiary.api.user.entity.User;
import com.oceandiary.api.user.security.token.Token;
import lombok.*;

public class UserResponse {
    // 테스트용 TODO: 삭제 필요
    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Login {
        private Long id;
        private String name;
        private String accessToken;

        public static Login build(User user, Token accessToken) {
            return Login.builder()
                    .id(user.getId())
                    .name(user.getName())
                    .accessToken(accessToken.getToken())
                    .build();
        }
    }
}
