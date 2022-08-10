package com.oceandiary.api.room.repository;

import com.oceandiary.api.room.entity.ParticipantOnRedis;
import org.springframework.data.repository.CrudRepository;

public interface ParticipantOnRedisRepository extends CrudRepository<ParticipantOnRedis, Long> {

}
