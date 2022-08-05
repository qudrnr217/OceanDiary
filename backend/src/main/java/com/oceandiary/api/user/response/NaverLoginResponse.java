package com.oceandiary.api.user.response;

import lombok.Data;

@Data
public class NaverLoginResponse {
    private boolean isExist;
    private String accessToken;
    private Long userId;
    private String name;
    private String oauthId;
}
