package com.oceandiary.api.room.response;

import com.oceandiary.api.common.category.Category;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

public class RoomResponse {

    @Getter
    @Builder
    public static class CreateRoom {
        private Long roomId;
        private Long participantId;
        private String token;
        private String connectionId;
    }

    @Getter
    @Builder
    public static class EnterRoom {
        private Long participantId;
        private String token;
        private String connectionId;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SearchRooms {
        private Long roomId;
        private Long createdBy;
        private Long imageId;
        private String title;
        private Integer maxNum;
        private Integer curNum;
        private Boolean isOpen;
    }
    @Getter
    @Builder
    public static class RoomDetail {
        RoomInfo roomInfo;
        List<ParticipantInfo> participantList;
        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class ParticipantInfo {
            private Long participantId;
            private String name;
            private LocalDateTime enterTime;
            private String token;
            private String connectionId;
        }

    }
    @Data
    @Getter
    @Builder
    public static class RoomInfo {
        private Long roomId;
        private String sessionId;
        private Category categoryId;
        private Long createdBy;
        private String title;
        private String rule;
        private Long imageId;
        private Integer maxNum;
        private Integer curNum;
        private Boolean isOpen;
    }
}
