package com.lisade.togeduck.dto.chat;

import com.lisade.togeduck.entity.enums.MessageAction;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageRequest {

    private Long userId;
    private Long roomId;
    private MessageAction action;
    private String message;
    private LocalDateTime createdAt;

    @Override
    public String toString() {
        return "ChatMessageRequest{" +
            "userId=" + userId +
            ", roomId=" + roomId +
            ", action=" + action +
            ", message='" + message + '\'' +
            ", createdAt=" + createdAt +
            '}';
    }
}
