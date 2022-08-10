package com.oceandiary.api.room.repository;


import com.oceandiary.api.room.entity.RoomOnRedis;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoomOnRedisRepository extends CrudRepository<RoomOnRedis, Long> {
    Optional<RoomOnRedis> findByCreatedBy(Long createdBy);
}
