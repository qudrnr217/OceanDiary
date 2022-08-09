package com.oceandiary.api.room.request;

import com.oceandiary.api.common.category.Category;
import lombok.Data;

public class RoomRequest {
    @Data
    public static class CreateRoom {
        private Category categoryId;
        private String title;
        private String rule;
        private Integer maxNum;
        private Boolean isOpen;
        private String pw;
    }

    @Data
    public static class EnterRoom {
        private String pw;
    }

    @Data
    public static class RoomSearchCondition {
        private Category category;
        private String title;
        private Long lastRoomId;
    }
}
