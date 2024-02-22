package com.lisade.togeduck.service;

import com.lisade.togeduck.dto.chat.ChatMessageRequest;
import com.lisade.togeduck.entity.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;

    @Transactional
    public void save(ChatMessageRequest chatMessageRequest) {
        ChatMessage chatMessage = chatMapper.toChatMessage(chatMessageRequest);
        chatRepository.save(chatMessage);
    }
}
