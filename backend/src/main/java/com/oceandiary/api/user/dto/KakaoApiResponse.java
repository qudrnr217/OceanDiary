package com.oceandiary.api.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

public class KakaoApiResponse {

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AccessTokenApiResponse{
        @JsonProperty("token_type")
        private String tokenType;

        @JsonProperty("access_token")
        private String accessToken;

        @JsonProperty("expires_in")
        private String expiresIn;

        @JsonProperty("refresh_token")
        private String refreshToken;

    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ProfileApiResponse{
        private Long id;
        private Properties properties;
        private KakaoAccount kakaoAccount;

        @Data
        public static class Properties{
            private String nickname;
        }
        @Data
        public static class KakaoAccount{
            private String email;
        }
    }

}
