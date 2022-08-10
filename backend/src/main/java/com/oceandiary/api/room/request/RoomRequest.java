package com.oceandiary.api.room.request;

import com.oceandiary.api.common.category.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class RoomRequest {
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreateRoom {
        private Category categoryId;
        private String title;
        private String rule;
        private Integer maxNum;
        private Boolean isOpen;
        private String pw;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EnterRoom {
        private String pw;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RoomSearchCondition {
        private Category category;
        private String title;
        private Long lastRoomId;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UpdateRoom {
        private String title;
        private String rule;
        private Integer maxNum;
        private Boolean isOpen;
        private String pw;
    }

}
