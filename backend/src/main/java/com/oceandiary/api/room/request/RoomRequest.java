package com.oceandiary.api.room.request;

import com.oceandiary.api.room.domain.Category;
import lombok.Data;

public class RoomRequest {
    @Data
    public static class CreateRoom {
        private Category categoryId;
        private Long userId;
        private String title;
        private String rule;
        private Integer maxNum;
        private Boolean isOpen;
        private String pw;
    }

    public static class EnterRoom {
        private String pw;
    }
}
