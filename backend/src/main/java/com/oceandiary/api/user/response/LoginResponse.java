package com.oceandiary.api.user.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class LoginResponse {
    @JsonProperty("isExist")
    private boolean isExist;
    private String accessToken;
    private Long userId;
    private String name;
    private String oauthId;
}
