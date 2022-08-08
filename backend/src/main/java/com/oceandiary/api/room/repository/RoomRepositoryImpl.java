package com.oceandiary.api.room.repository;

import com.oceandiary.api.room.dto.RoomSearchCondition;
import com.oceandiary.api.room.entity.Room;
import com.oceandiary.api.room.response.RoomResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static com.oceandiary.api.room.entity.QRoom.room;


public class RoomRepositoryImpl implements RoomRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public RoomRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public Page<RoomResponse.SearchRooms> search(RoomSearchCondition condition, Pageable pageable) {

        List<Room> content = queryFactory
                .select(room)
                .from(room)
                .where(
                        room.category.eq(condition.getCategory()),
                        room.deletedAt.isNull(),
                        room.id.lt(condition.getLastRoomId()),
                        condition.getTitle() == null ? null : room.title.like(condition.getTitle())
                )
                .offset(0)
                .orderBy(room.id.desc())
                .limit(pageable.getPageSize())
                .fetch();

        List<RoomResponse.SearchRooms> searchedRooms= new ArrayList<>();
        for (Room searchedRoom : content) {
            searchedRooms.add(RoomResponse.SearchRooms.builder()
                    .roomId(searchedRoom.getId())
                    .createdBy(searchedRoom.getCreatedBy().getId())
                    .image(searchedRoom.getImage() != null ? searchedRoom.getImage().getName() : null)
                    .title(searchedRoom.getTitle())
                    .maxNum(searchedRoom.getMaxNum())
                    .isOpen(searchedRoom.getIsOpen())
                    .build());
        }
        long total = queryFactory
                .select(room)
                .from(room)
                .where(
                        room.category.eq(condition.getCategory()),
                        room.deletedAt.isNull(),
                        room.id.lt(condition.getLastRoomId()),
                        condition.getTitle() == null ? null : room.title.like(condition.getTitle())
                )
                .offset(0)
                .orderBy(room.id.desc())
                .limit(pageable.getPageSize())
                .fetchCount();

        return new PageImpl<>(searchedRooms, pageable, total);
    }
}
