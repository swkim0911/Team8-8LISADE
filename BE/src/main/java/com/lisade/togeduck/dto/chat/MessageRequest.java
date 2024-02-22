package com.lisade.togeduck.dto.chat;

import com.lisade.togeduck.entity.enums.MessageType;
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
public class MessageRequest {

    private Long userId;
    private Long roomId;
    private MessageType action;
    private String message;
    private LocalDateTime createdAt;
}
