package com.lisade.togeduck.repository;

import com.lisade.togeduck.dto.response.ChatRoomListResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface ChatRoomRepositoryCustom {

    Slice<ChatRoomListResponse> findJoinedChatRooms(Pageable pageable, Long userId);
}
