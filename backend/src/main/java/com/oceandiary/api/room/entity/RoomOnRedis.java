package com.oceandiary.api.room.entity;

import io.openvidu.java.client.Session;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@Builder
@RedisHash(value = "room")
public class RoomOnRedis {
    @Id
    private Long id;
    private Long createdBy;
    private String sessionId;
}
