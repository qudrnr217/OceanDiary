package com.oceandiary.api.room.dto;

import com.oceandiary.api.common.category.Category;
import com.oceandiary.api.room.entity.Room;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

public class RoomResponse {
    @Getter
    @Builder
    public static class OnlyId {
        private Long roomId;
    }
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
        public static RoomResponse.SearchRooms build(Room room, Integer curNum){
            return SearchRooms.builder()
                    .roomId(room.getId())
                    .createdBy(room.getUser().getId())
                    .imageId(room.getImage() != null ? room.getImage().getId() : null)
                    .title(room.getTitle())
                    .maxNum(room.getMaxNum())
                    .curNum(curNum)
                    .isOpen(room.getIsOpen())
                    .build();
        }
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
            private Long userId;
            private Long participantId;
            private String name;
            private LocalDateTime enterTime;
            private String token;
            private String connectionId;
        }

    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
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
