package com.oceandiary.api.room.dto;

import com.oceandiary.api.common.category.Category;
import lombok.Data;

@Data
public class RoomSearchCondition {
    private Category category;
    private String title;
    private Long lastRoomId;
}
