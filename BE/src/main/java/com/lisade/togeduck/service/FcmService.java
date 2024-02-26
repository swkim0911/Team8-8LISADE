package com.lisade.togeduck.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
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
                .setNotification(
                    Notification.builder()
                        .setTitle(chatMessageRequest.getRoomName())
                        .setBody(chatMessageRequest.getMessage())
                        .build())
                .setTopic(String.valueOf(chatMessageRequest.getRoomId()))
                .build()
        );
    }
}
