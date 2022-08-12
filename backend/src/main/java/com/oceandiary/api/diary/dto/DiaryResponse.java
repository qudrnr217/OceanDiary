package com.oceandiary.api.diary.dto;


import com.oceandiary.api.common.category.Category;
import com.oceandiary.api.diary.entity.Stamp;
import com.oceandiary.api.user.entity.User;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    @NoArgsConstructor (access = AccessLevel.PRIVATE)
    @AllArgsConstructor (access = AccessLevel.PRIVATE)
    public static class GetStamps {
        private List<DiaryResponse.GetStamp> stamps;
        public static DiaryResponse.GetStamps build(List<Stamp> stampList){
            return DiaryResponse.GetStamps.builder()
                    .stamps(stampList.stream().map(DiaryResponse.GetStamp::build).collect(Collectors.toList()))
                    .build();
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public static class GetDiaryContents{

        private Long id;
        private String name;
        private String email;
        private LocalDate birth;
        private LocalDateTime visitedAt;
        private List<DiaryResponse.GetStamp> stamps = new ArrayList<>();

        public static DiaryResponse.GetDiaryContents build(User user){
            return GetDiaryContents.builder()
                    .id(user.getId())
                    .name(user.getName())
                    .email(user.getEmail())
                    .birth(user.getBirth())
                    .visitedAt(user.getVisitedAt())
                    .stamps(DiaryResponse.GetStamps.build(user.getStamps()).getStamps())
                    .build();
        }

    }
}
