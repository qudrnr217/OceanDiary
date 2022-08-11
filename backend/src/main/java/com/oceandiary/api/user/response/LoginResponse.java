package com.oceandiary.api.user.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.oceandiary.api.user.entity.User;
import com.oceandiary.api.user.security.token.Token;
import lombok.*;

public class LoginResponse {

    @Builder
    @Getter
    @Setter
    public static class Join {
        private Long userId;
        private String name;
        private String accessToken;
        private String refreshToken;
    }

    @Builder
    @Getter
    @Setter
    public static class Login {
        @JsonProperty("isExist")
        private boolean isExist;
        private String accessToken;
        private Long userId;
        private String name;
        private String oauthId;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class State {
        private String state;
    }
    
    @Getter
    @Builder(access = AccessLevel.PRIVATE)
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Auth {
        private Long userId;
        private String name;
        private String accessToken;
        @JsonIgnore
        private String refreshToken;

        public static Auth build(User user, Token accessToken, Token refreshToken) {
            return Auth.builder()
                    .userId(user.getId())
                    .name(user.getName())
                    .accessToken(accessToken.getToken())
                    .refreshToken(refreshToken.getToken())
                    .build();
        }
    }
}
