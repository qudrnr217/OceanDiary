package com.oceandiary.api.room.repository;

import com.oceandiary.api.room.entity.Room;
import com.oceandiary.api.room.request.RoomRequest;
import com.oceandiary.api.room.response.RoomResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import java.util.List;

import static com.oceandiary.api.file.entity.QImage.image;
import static com.oceandiary.api.room.entity.QRoom.room;
import static com.querydsl.jpa.JPAExpressions.select;


@Slf4j
public class RoomRepositoryImpl implements RoomRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public RoomRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<RoomResponse.SearchRooms> search(RoomRequest.RoomSearchCondition condition, Pageable pageable) {

        List<RoomResponse.SearchRooms> content = queryFactory
                .select(Projections.fields(
                        RoomResponse.SearchRooms.class,
                        room.id.as("roomId"),
                        room.createdBy.id.as("createdBy"),
                        room.image.id.as("imageId"),
                        room.title,
                        room.maxNum,
                        room.isOpen
                ))
                .from(room)
                .leftJoin(room.image, image)
                .where(
                        room.category.eq(condition.getCategory()),
                        room.deletedAt.isNull(),
                        condition.getLastRoomId() != 0 ? room.id.lt(condition.getLastRoomId()) : room.id.loe(select(room.id.max()).from(room)),
                        condition.getTitle() == null ? null : room.title.like(condition.getTitle())
                )
                .offset(0)
                .orderBy(room.id.desc())
                .limit(pageable.getPageSize())
                .fetch();

        log.info("content: {}", content);

        long total = queryFactory
                .select(room)
                .from(room)
                .where(
                        room.category.eq(condition.getCategory()),
                        room.deletedAt.isNull(),
                        condition.getTitle() == null ? null : room.title.like(condition.getTitle())
                )
                .fetchCount();

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public List<Room> searchUndeletedRooms() {
        return queryFactory
                .selectFrom(room)
                .where(
                        room.deletedAt.isNull()
                )
                .fetch();
    }

//    @Override
//    public void updateUndeletedRoomToDeletedRoom(Long roomId) {
//        queryFactory
//                .update(room)
//                .set(room.deletedAt, LocalDateTime.now())
//                .where(room.id.eq(roomId))
//                .execute();
//    }

}
