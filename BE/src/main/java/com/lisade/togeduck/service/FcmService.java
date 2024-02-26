package com.lisade.togeduck.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.lisade.togeduck.dto.request.ChatMessageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FcmService {

    public void sendNotification(ChatMessageRequest chatMessageRequest)
        throws FirebaseMessagingException {
        FirebaseMessaging.getInstance().send(
            Message.builder()
                .putData("roomId", String.valueOf(chatMessageRequest.getRoomId()))
                .putData("sender", chatMessageRequest.getSender())
                .putData("roomName", chatMessageRequest.getRoomName())
                .putData("createdAt", chatMessageRequest.getCreatedAt())
                .putData("message", chatMessageRequest.getMessage())
                .putData("action", chatMessageRequest.getAction())
                .setTopic(String.valueOf(chatMessageRequest.getRoomId()))
                .build()
        );
    }
}
