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
    private Map<Long, String> participants;

    public void addParticipant(Long participantId, String token) {
        this.participants.put(participantId, token);
    }

    public void removeParticipant(Long participantId) {
        this.participants.remove(participantId);
    }
}
