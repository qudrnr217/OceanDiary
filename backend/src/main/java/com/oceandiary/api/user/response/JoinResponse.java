package com.oceandiary.api.user.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class JoinResponse {
    private Long userId;
    private String name;
    private String accessToken;
}
