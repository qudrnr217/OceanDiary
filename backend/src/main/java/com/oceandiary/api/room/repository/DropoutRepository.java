package com.oceandiary.api.room.repository;

import com.oceandiary.api.room.entity.Dropout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DropoutRepository extends JpaRepository<Dropout, Long> {
    Optional<Dropout> findByUser_idAndRoom_id(Long userId, Long roomId);
}
