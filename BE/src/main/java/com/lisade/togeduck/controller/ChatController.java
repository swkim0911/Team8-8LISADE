package com.lisade.togeduck.controller;

import com.lisade.togeduck.dto.request.ChatMessageRequest;
import com.lisade.togeduck.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    private final SimpMessageSendingOperations simpleMessageSendingOperations;

    @MessageMapping(value = "/chat/join")
    public void join(@RequestBody ChatMessageRequest chatMessageRequest) {
        String message = chatMessageRequest.getSender() + "님이 입장하셨습니다.";

        chatMessageRequest.setMessage(message);
        chatService.save(chatMessageRequest);
        simpleMessageSendingOperations.convertAndSend(
            "/topic/message/" + chatMessageRequest.getRoomId(), message);
    }

    @MessageMapping(value = "/chat/message")
    @SendTo("/topic/message")
    public void message(@RequestBody ChatMessageRequest chatMessageRequest) {

    }
}
