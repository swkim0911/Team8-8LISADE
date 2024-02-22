package com.lisade.togeduck.Interceptor;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@Component
public class ChannelInboundInterceptor implements ChannelInterceptor {

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        System.out.println("header.getCommand() = " + accessor.getCommand());
        System.out.println("header.getDestination() = " + accessor.getDestination());
        System.out.println("accessor.getSessionId() = " + accessor.getSessionId());
        return message;
    }
}
