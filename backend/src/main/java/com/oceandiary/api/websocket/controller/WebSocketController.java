package com.oceandiary.api.websocket.controller;

import com.oceandiary.api.websocket.dto.RoomMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@Slf4j
public class WebSocketController {

    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping(value = "/rooms/{roomId}")
    public void enter(RoomMessage roomMessage, @DestinationVariable Long roomId) {
        RoomMessage newRoomMessage = RoomMessage.of(roomMessage, roomMessage.getName().toString() + "님이 채팅방에 참여하였습니다.", roomId);
        simpMessagingTemplate.convertAndSend("/sub/rooms/" + roomId, newRoomMessage);
    }

    @MessageMapping(value = "/rooms/{roomId}/messages")
    public void message(RoomMessage roomMessage, @DestinationVariable Long roomId) {
        simpMessagingTemplate.convertAndSend("/sub/rooms/" + roomId, roomMessage);
    }
}
