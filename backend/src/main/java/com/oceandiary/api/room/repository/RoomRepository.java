package com.oceandiary.api.room.repository;

import com.oceandiary.api.room.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long>, RoomRepositoryCustom {
}
