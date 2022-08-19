package com.oceandiary.api.websocket.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RoomMessage {

    private Long roomId;
    private Long participantId; // publisher id
    private String name; // publisher name
    private String message;

    public static RoomMessage of(RoomMessage roomMessage, String message, Long roomId) {
        return RoomMessage.builder()
                .roomId(roomId)
                .participantId(roomMessage.participantId)
                .name(roomMessage.name)
                .message(message)
                .build();
    }
}
