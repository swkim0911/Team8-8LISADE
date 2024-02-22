package com.lisade.togeduck.controller;

import com.lisade.togeduck.dto.chat.ChatMessageRequest;
import com.lisade.togeduck.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private ChatService chatService;

    // app/chat/enter
    @MessageMapping(value = "/chat/join")
    @SendTo("/topic/message")
    public void join(@RequestBody ChatMessageRequest chatMessageRequest) {
        /**
         * 사용자가 /pub/chat/enter uri로 메시지를 보내면 그건 채팅방 입장 요청
         * 전달 받은 메시지를 mongodb에 저장
         * 이 메시지를 /topic/message에 전송하면 /topic/message 구독자들에게 메시지가 전송됨
         * 전송한 user도 /topic/message 구독자가 됨.
         */
        System.out.println("chatMessageRequest = " + chatMessageRequest);
//        chatService.save(chatMessageRequest, JOIN);
    }

    @MessageMapping(value = "/chat/message")
    @SendTo("/topic/message")
    public void message(@RequestBody ChatMessageRequest chatMessageRequest) {

    }
}
