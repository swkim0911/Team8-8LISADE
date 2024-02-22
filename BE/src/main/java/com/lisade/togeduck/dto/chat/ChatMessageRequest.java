package com.lisade.togeduck.dto.chat;

import com.lisade.togeduck.entity.enums.MessageAction;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageRequest {

    private Long userId;
    private Long roomId;
    private String sender;
    private String message;
    private MessageAction action;
    private LocalDateTime createdAt;

    public void setMessage(String message) {
        this.message = message;
    }
}
