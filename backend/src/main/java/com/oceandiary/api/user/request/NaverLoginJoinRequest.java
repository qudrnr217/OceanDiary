package com.oceandiary.api.user.request;

import com.oceandiary.api.user.domain.SocialProvider;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
@Data
public class NaverLoginJoinRequest {
    private String email;
    private String name;
    private LocalDate birth;
    private String oauthId;
}
