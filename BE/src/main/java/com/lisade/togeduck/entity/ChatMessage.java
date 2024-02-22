package com.lisade.togeduck.entity;

import com.lisade.togeduck.entity.enums.MessageType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document
@NoArgsConstructor
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private Long userId;
    private Long roomId;
    private String nickname;
    private String message;
    private MessageType messageAction;
    private LocalDateTime createdAt;

}
