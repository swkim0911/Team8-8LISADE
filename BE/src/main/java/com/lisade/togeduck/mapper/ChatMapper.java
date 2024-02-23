package com.lisade.togeduck.mapper;

import com.lisade.togeduck.dto.request.ChatMessageRequest;
import com.lisade.togeduck.entity.ChatMessage;

public class ChatMapper {

    public static ChatMessage toChatMessage(ChatMessageRequest chatMessageRequest) {
        return ChatMessage.builder()
            .userId(chatMessageRequest.getUserId())
            .roomId(chatMessageRequest.getRoomId())
            .sender(chatMessageRequest.getSender())
            .message(chatMessageRequest.getMessage())
            .action(chatMessageRequest.getAction())
            .createdAt(chatMessageRequest.getCreatedAt()).build();
    }
}
