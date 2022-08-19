package com.oceandiary.api.room.entity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;

@Getter
@Builder
@RedisHash(value = "room")
public class RoomOnRedis {
    @Id
    private Long id;
    private Long createdBy;
    private String sessionId;
}
