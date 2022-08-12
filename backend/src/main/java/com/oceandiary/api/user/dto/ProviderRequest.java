package com.oceandiary.api.user.dto;

import lombok.Data;

import java.time.LocalDate;

public class ProviderRequest {
    @Data
    public static class JoinRequest {
        private String email;
        private String name;
        private LocalDate birth;
        private String oauthId;
    }

    @Data
    public static class LoginRequest {
        private String code;
    }

}
