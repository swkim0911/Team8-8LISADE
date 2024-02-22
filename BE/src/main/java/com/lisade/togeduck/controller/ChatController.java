package com.lisade.togeduck.controller;

import com.lisade.togeduck.dto.chat.MessageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate template;

    // /pub/chat/enter
    @MessageMapping(value = "/chat/enter")
    @SendTo("/topic/message") // 이 경로를 구독한 user에게 전송
    public void enter(@RequestBody MessageRequest message) {
        /**
         * 사용자가 /pub/chat/enter uri로 메시지를 보내면 그건 채팅방 입장 요청
         * 전달 받은 메시지를 mongodb에 저장
         * 이 메시지를 /topic/message에 전송하면 /topic/message 구독자들에게 메시지가 전송됨
         * 전송한 user도 /topic/message 구독자가 됨.
         *
         */
    }

    @MessageMapping(value = "/chat/message")
    public void message(@RequestBody MessageRequest message) {
        template.convertAndSend("/chat/room/" + message.getRoomId(), message);

    }
}
