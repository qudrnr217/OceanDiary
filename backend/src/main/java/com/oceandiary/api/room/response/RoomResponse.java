package com.oceandiary.api.room.response;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

public class RoomResponse {

    @Getter
    @Builder
    public static class CreateRoom {
        private Long roomId;
        private Long participantId;
        private String token;
    }

    @Getter
    @Builder
    public static class EnterRoom {
        private Long participantId;
        private String token;
    }

    @Data
    public static class SearchRooms {
        private Long roomId;
        private Long createdBy;
        private String image;
        private String title;
        private Integer maxNum;
        private Integer curNum;
        private Boolean isOpen;

    }
}
