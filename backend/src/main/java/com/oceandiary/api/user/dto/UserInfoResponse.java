package com.oceandiary.api.user.dto;

import com.oceandiary.api.diary.dto.DiaryResponse;
import com.oceandiary.api.user.entity.User;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserInfoResponse {
    private Long id;
    private String name;
    private String email;
    private LocalDate birth;
    private LocalDateTime visitedAt;
    private List<DiaryResponse.GetStamp> stamps = new ArrayList<>();

    public static UserInfoResponse build(User user) {
        return UserInfoResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .birth(user.getBirth())
                .visitedAt(user.getVisitedAt())
                .stamps(DiaryResponse.GetStamps.build(user.getStamps()).getStamps())
                .build();

    }

}
