package com.lisade.togeduck.repository;

import static com.lisade.togeduck.entity.QChatRoom.chatRoom;
import static com.lisade.togeduck.entity.QFestival.festival;
import static com.lisade.togeduck.entity.QRoute.route;
import static com.lisade.togeduck.entity.QUserChatRoom.userChatRoom;

import com.lisade.togeduck.dto.response.ChatRoomListResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ChatRoomRepositoryImpl implements ChatRoomRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<ChatRoomListResponse> findJoinedChatRooms(Pageable pageable, Long userId) {
        JPAQuery<ChatRoomListResponse> query = queryFactory.select(Projections.constructor(
                ChatRoomListResponse.class,
                chatRoom.roomName,
                chatRoom.numberOfMembers,
                festival.thumbnailPath))
            .from(route)
            .join(chatRoom)
            .on(chatRoom.route.id.eq(route.id))
            .join(userChatRoom)
            .on(userChatRoom.chatRoom.id.eq(chatRoom.id))
            .join(festival)
            .on(festival.id.eq(route.festival.id))
            .where(userChatRoom.user.id.eq(userId));

        Sort sort = pageable.getSort();
        Sort.Order order = sort.getOrderFor("createdAt");
        if (order != null) {
            query.orderBy(route.createdAt.desc());
        }

        List<ChatRoomListResponse> chatRooms = query.limit(pageable.getPageSize() + 1).fetch();

        boolean hasNext = false;
        if (chatRooms.size() > pageable.getPageSize()) {
            chatRooms.remove(pageable.getPageSize());
            hasNext = true;
        }
        return new SliceImpl<>(chatRooms, pageable, hasNext);
    }
}
