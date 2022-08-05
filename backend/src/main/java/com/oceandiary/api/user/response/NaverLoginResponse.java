package com.oceandiary.api.user.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class NaverLoginResponse {

    @JsonProperty("isExist")
    private boolean isExist;
    private String accessToken;
    private Long userId;
    private String name;
    private String oauthId;
}
