package com.oceandiary.api.diary.dto;


import com.oceandiary.api.common.category.Category;
import com.oceandiary.api.diary.entity.Stamp;
import com.oceandiary.api.user.entity.User;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


public class DiaryResponse {
    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class StampOnlyId {
        private Long id;

        public static StampOnlyId build(Stamp stamp) {
            return StampOnlyId.builder()
                    .id(stamp.getId())
                    .build();
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class GetStamp {
        private Long id;
        private Category category;
        private String totalTime;

        public static GetStamp build(Stamp stamp) {
            return GetStamp.builder()
                    .id(stamp.getId())
                    .category(stamp.getCategory())
                    .totalTime(stamp.getTotalTime())
                    .build();
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class GetDiaryContents{
        private UserInfo userInfo;
        private List<DiaryResponse.GetStamp> stamps;

        public static DiaryResponse.GetDiaryContents build(User user, List<Stamp> stamps){
            return GetDiaryContents.builder()
                    .userInfo(UserInfo.build(user))
                    .stamps(stamps.stream().map(DiaryResponse.GetStamp::build).collect(Collectors.toList()))
                    .build();
        }

    }

    @Getter
    @Builder
    public static class UserOnlyId{
        private Long userId;

        public static UserOnlyId build(User user){
            return UserOnlyId.builder()
                    .userId(user.getId())
                    .build();
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class UserInfo{
        private Long id;
        private String name;
        private String email;
        private LocalDate birth;
        private LocalDateTime visitedAt;

        public static UserInfo build(User user){
            return UserInfo.builder()
                    .id(user.getId())
                    .name(user.getName())
                    .email(user.getEmail())
                    .birth(user.getBirth())
                    .visitedAt(user.getVisitedAt())
                    .build();
        }
    }
}
