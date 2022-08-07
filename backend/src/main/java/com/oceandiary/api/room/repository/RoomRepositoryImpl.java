package com.oceandiary.api.room.repository;

import com.oceandiary.api.room.dto.RoomSearchCondition;
import com.oceandiary.api.room.response.RoomResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import java.util.List;

import static com.oceandiary.api.room.entity.QRoom.room;


public class RoomRepositoryImpl implements RoomRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public RoomRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public Page<RoomResponse.SearchRooms> search(RoomSearchCondition condition, Pageable pageable) {
        List<RoomResponse.SearchRooms> content = queryFactory
                .select(Projections.bean(RoomResponse.SearchRooms.class,
                        room.id.as("roomId"),
                        room.createdBy,
                        room.image.name.as("image"),
                        room.title,
                        room.maxNum,
                        room.isOpen))
                .from(room)
                .where(
                        room.category.eq(condition.getCategory()),
                        room.deletedAt.isNull(),
                        room.id.lt(condition.getLastRoomId()),
                        room.title == null ? null : room.title.like(condition.getTitle())
                )
                .offset(0)
                .limit(pageable.getPageSize())
                .fetch();
        long total = queryFactory
                .select(Projections.bean(RoomResponse.SearchRooms.class,
                        room.createdBy, room.image.name.as("image"), room.title, room.maxNum, room.isOpen))
                .from(room)
                .where(
                        room.category.eq(condition.getCategory()),
                        room.deletedAt.isNull(),
                        room.id.lt(condition.getLastRoomId()),
                        room.title == null ? null : room.title.like(condition.getTitle())
                )
                .offset(0)
                .limit(pageable.getPageSize())
                .fetchCount();

        return new PageImpl<>(content, pageable, total);
    }
}
