package com.oceandiary.api.room.repository;

import com.oceandiary.api.room.dto.RoomSearchCondition;
import com.oceandiary.api.room.response.RoomResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoomRepositoryCustom {
    Page<RoomResponse.SearchRooms> search(RoomSearchCondition condition, Pageable pageable);
}
