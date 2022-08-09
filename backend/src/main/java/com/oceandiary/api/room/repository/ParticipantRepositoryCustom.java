package com.oceandiary.api.room.repository;

import com.oceandiary.api.room.response.RoomResponse;

import java.util.List;
import java.util.Set;

public interface ParticipantRepositoryCustom {

    List<RoomResponse.RoomDetail.ParticipantInfo> findActiveParticipants(Set<Long> activeParticipants);
}
