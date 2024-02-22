package com.lisade.togeduck.repository;

import com.lisade.togeduck.entity.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ChatRepository {

    private final MongoTemplate mongoTemplate;

    public void save(ChatMessage chatMessage) {
        String chatRoomName = "chat_room_" + chatMessage.getRoomId();
        mongoTemplate.save(chatMessage, chatRoomName);
    }
}
