package com.lisade.togeduck.dto.request;

import com.lisade.togeduck.entity.enums.MessageAction;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageRequest {

    private Long userId;
    private Long roomId;
    private String sender;
    @Setter
    private String message;
    private MessageAction action;
    private LocalDateTime createdAt;

}
