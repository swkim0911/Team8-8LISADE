package com.lisade.togeduck.controller;

import com.lisade.togeduck.dto.chat.ChatMessageRequest;
import com.lisade.togeduck.entity.ChatRoom;
import com.lisade.togeduck.service.ChatRoomService;
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

    private ChatService chatService;
    private ChatRoomService chatRoomService;

    private final SimpMessageSendingOperations simpleMessageSendingOperations;

    /**
     * 사용자가 /pub/chat/join uri로 메시지를 보내면 그건 채팅방 입장 요청 전달 받은 메시지를 mongodb에 저장 이 메시지를 /topic/message에
     * 전송하면 /topic/message 구독자들에게 메시지가 전송됨 전송한 user도 /topic/message 구독자가 됨.
     */

    @MessageMapping(value = "/chat/join")
    public void join(@RequestBody ChatMessageRequest chatMessageRequest) {
        // 1. 채팅방에 user 가 이미 join 했는지 확인
        if (chatRoomService.exist(chatMessageRequest.getUserId(), chatMessageRequest.getRoomId())) {
            simpleMessageSendingOperations.convertAndSend(
                "/topic/message/" + chatMessageRequest.getRoomId(),
                chatMessageRequest.getSender() + "님이 입장하셨습니다.");
            // 1.1 client에게 이전 채팅 목록 전달 Slice

        }
        // 2.1 그 채팅방
        ChatRoom chatRoom = chatRoomService.get(chatMessageRequest.getRoomId());

//        chatService.save(chatMessageRequest, JOIN);
    }

    @MessageMapping(value = "/chat/message")
    @SendTo("/topic/message")
    public void message(@RequestBody ChatMessageRequest chatMessageRequest) {

    }
}
