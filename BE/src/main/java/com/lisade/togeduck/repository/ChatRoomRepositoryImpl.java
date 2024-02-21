package com.lisade.togeduck.repository;

import com.lisade.togeduck.dto.response.ChatRoomListResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public class ChatRoomRepositoryImpl implements ChatRoomRepositoryCustom {

    @Override
    public Slice<ChatRoomListResponse> findJoinedChatRooms(Pageable pageable, Long userId) {
        return null;
    }
}
