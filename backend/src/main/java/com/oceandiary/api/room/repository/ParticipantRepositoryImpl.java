package com.oceandiary.api.room.repository;


import com.oceandiary.api.room.dto.RoomResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Set;

import static com.oceandiary.api.room.entity.QParticipant.participant;
import static com.oceandiary.api.user.entity.QUser.user;

public class ParticipantRepositoryImpl implements ParticipantRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public ParticipantRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<RoomResponse.RoomDetail.ParticipantInfo> findActiveParticipants(Set<Long> activeParticipants) {
        return queryFactory
                .select(Projections.fields(
                        RoomResponse.RoomDetail.ParticipantInfo.class,
                        user.id.as("userId"),
                        participant.id.as("participantId"),
                        participant.name,
                        participant.enterDate.as("enterTime")
                ))
                .from(participant)
                .leftJoin(participant.user, user)
                .where(
                        participant.id.in(activeParticipants),
                        participant.exitDate.isNull()
                )
                .fetch();
    }
}
