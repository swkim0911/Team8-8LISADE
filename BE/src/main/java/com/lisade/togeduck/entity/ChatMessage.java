package com.lisade.togeduck.entity;

import com.lisade.togeduck.entity.enums.MessageAction;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private Long userId;
    private Long roomId;
    private String nickname;
    private String message;
    private MessageAction messageAction;
    private LocalDateTime createdAt;

}
