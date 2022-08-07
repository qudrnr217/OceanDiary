package com.oceandiary.api.user.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class JoinRequest {
    private String email;
    private String name;
    private LocalDate birth;
    private String oauthId;
}
