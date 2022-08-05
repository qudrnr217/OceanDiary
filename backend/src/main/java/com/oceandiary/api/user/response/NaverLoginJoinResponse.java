package com.oceandiary.api.user.response;

import lombok.Data;

@Data
public class NaverLoginJoinResponse {
    private Long userId;
    private String name;
    private String accessToken;
}
