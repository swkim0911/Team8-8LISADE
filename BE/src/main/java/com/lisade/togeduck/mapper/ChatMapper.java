package com.lisade.togeduck.mapper;

import com.lisade.togeduck.dto.chat.ChatMessageRequest;
import com.lisade.togeduck.entity.ChatMessage;

public class ChatMapper {

    public static ChatMessage toChatMessage(ChatMessageRequest chatMessageRequest) {
        return ChatMessage.builder()
            .userId(chatMessageRequest.getUserId())
            .roomId(chatMessageRequest.getRoomId())
            .nickname(null) //todo controller에서 @Login user로 받아오기
            .message(chatMessageRequest.getMessage())
            .messageAction(chatMessageRequest.getAction())
            .createdAt(chatMessageRequest.getCreatedAt()).build();
    }
}
