package com.oceandiary.api.room.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    @Builder
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
