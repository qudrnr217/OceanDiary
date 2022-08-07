package com.oceandiary.api.room.dto;

import com.oceandiary.api.room.domain.Category;
import lombok.Data;

import java.util.List;

@Data
public class RoomSearchCondition {
    private Category category;
    private String title;
    private Long lastRoomId;
    private List<Long> roomIds;
}
