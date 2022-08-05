package com.oceandiary.api.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


public class NaverApiResponse {

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AccessTokenApiResponse {
        @JsonProperty("access_token")
        private String accessToken;

        @JsonProperty("refresh_token")
        private String refreshToken;

        @JsonProperty("token_type")
        private String tokenType;

        @JsonProperty("expires_in")
        private int expiresIn;

        @JsonProperty("error")
        private String error;

        @JsonProperty("error_description")
        private String errorDescription;
    }


    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ProfileApiResponse {
        private String resultcode;
        private String message;
        private Response response;

        @Data
        public static class Response {
            private String email;
            private String id;
            private String name;
        }
    }

}
