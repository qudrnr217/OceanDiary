package com.oceandiary.api.room.entity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;
import java.util.Map;

@Getter
@Builder
@RedisHash(value = "participant")
public class ParticipantOnRedis {
    @Id
    private Long id;
    private Map<Long, String> participantTokenMap;

    private Map<Long, String> participantConnectionMap;

    public void addParticipant(Long participantId, String token, String connectionId) {
        this.participantTokenMap.put(participantId, token);
        this.participantConnectionMap.put(participantId, connectionId);
    }

    public void removeParticipant(Long participantId) {
        this.participantTokenMap.remove(participantId);
        this.participantConnectionMap.remove(participantId);
    }
}
