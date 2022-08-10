package com.oceandiary.api.user.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class ProviderResponse {

    @Builder
    @Getter
    @Setter
    public static class JoinResponse {
        private Long userId;
        private String name;
        private String accessToken;
    }

    @Builder
    @Getter
    @Setter
    public static class LoginResponse {
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
    public static class StateResponse {
        private String state;
    }
}
