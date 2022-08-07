package com.oceandiary.api.room.entity;

import io.openvidu.java.client.Session;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;

@Getter
@AllArgsConstructor
@Builder
@RedisHash(value = "room")
public class RoomOnRedis {
    @Id
    private Long id;
    private Long createdBy;
    private Session session;
}
