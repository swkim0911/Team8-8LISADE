package com.lisade.togeduck.controller;

import com.lisade.togeduck.dto.chat.ChatMessageRequest;
import com.lisade.togeduck.entity.ChatRoom;
import com.lisade.togeduck.entity.User;
import com.lisade.togeduck.service.ChatRoomService;
import com.lisade.togeduck.service.ChatService;
import com.lisade.togeduck.service.UserChatRoomService;
import com.lisade.togeduck.service.UserService;
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
    private final ChatRoomService chatRoomService;
    private final UserChatRoomService userChatRoomService;
    private final UserService userService;
    private final SimpMessageSendingOperations simpleMessageSendingOperations;

    /**
     * 사용자가 /pub/chat/join uri로 메시지를 보내면 그건 채팅방 입장 요청 전달 받은 메시지를 mongodb에 저장 이 메시지를 /topic/message에
     * 전송하면 /topic/message 구독자들에게 메시지가 전송됨 전송한 user도 /topic/message 구독자가 됨.
     */


    @MessageMapping(value = "/chat/join")
    public void join(@RequestBody ChatMessageRequest chatMessageRequest) {
        //todo @Login User user 하면 test 환경에서 안들어옴. 그래서 일단 User 조회
        User findUser = userService.get(chatMessageRequest.getUserId());
        String message = chatMessageRequest.getSender() + "님이 입장하셨습니다.";
        chatRoomService.create(findUser, 1L);

        ChatRoom chatRoom = chatRoomService.get(chatMessageRequest.getRoomId());
        userChatRoomService.create(findUser, chatRoom);
        chatMessageRequest.setMessage(message);
        chatService.save(chatMessageRequest);
        simpleMessageSendingOperations.convertAndSend(
            "/topic/message/" + chatMessageRequest.getRoomId(), message);
    }

    @MessageMapping(value = "/chat/rejoin")
    public void rejoin(@RequestBody ChatMessageRequest chatMessageRequest) {
        // 1. 채팅방에 user 가 이미 join 했는지 확인
//        if (chatRoomService.exist(chatMessageRequest.getUserId(), chatMessageRequest.getRoomId())) {
//            simpleMessageSendingOperations.convertAndSend(
//                "/topic/message/" + chatMessageRequest.getRoomId(),
//                message);
//        }
        // 2.1 그 채팅방에 이미 있을 때
    }

    @MessageMapping(value = "/chat/message")
    @SendTo("/topic/message")
    public void message(@RequestBody ChatMessageRequest chatMessageRequest) {

    }
}
