package com.lisade.togeduck.service;

import com.lisade.togeduck.entity.ChatRoom;
import com.lisade.togeduck.entity.User;
import com.lisade.togeduck.entity.UserChatRoom;
import com.lisade.togeduck.repository.UserChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserChatRoomService {

    private final UserChatRoomRepository userChatRoomRepository;

    public void create(User user, ChatRoom chatRoom) {
        UserChatRoom userChatRoom = UserChatRoom.builder()
            .user(user)
            .chatRoom(chatRoom)
            .build();
        userChatRoomRepository.save(userChatRoom);
    }
}
