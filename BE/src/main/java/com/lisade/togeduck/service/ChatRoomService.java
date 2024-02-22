package com.lisade.togeduck.service;

import com.lisade.togeduck.dto.response.ChatRoomListResponse;
import com.lisade.togeduck.entity.ChatRoom;
import com.lisade.togeduck.entity.Route;
import com.lisade.togeduck.entity.User;
import com.lisade.togeduck.entity.UserChatRoom;
import com.lisade.togeduck.exception.RouteNotFoundException;
import com.lisade.togeduck.repository.ChatRoomRepository;
import com.lisade.togeduck.repository.RouteRepository;
import com.lisade.togeduck.repository.UserChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final UserChatRoomRepository userChatRoomRepository;
    private final RouteRepository routeRepository;

    @Transactional
    public void create(User user, Long routeId, Long festivalId) {
        Route findRoute = routeRepository.findById(routeId)
            .orElseThrow(RouteNotFoundException::new);

        ChatRoom chatRoom = ChatRoom.builder()
            .route(findRoute)
            .roomName(festivalId + " 채팅방")
            .numberOfMembers(1)
            .build();
        chatRoomRepository.save(chatRoom);

        UserChatRoom userChatRoom = UserChatRoom.builder()
            .user(user)
            .chatRoom(chatRoom)
            .build();
        userChatRoomRepository.save(userChatRoom);
    }

    public Slice<ChatRoomListResponse> get(Pageable pageable, Long userId) {
        return chatRoomRepository.findJoinedChatRooms(pageable, userId);
    }

}
