package com.example.be.model.dto.facade.request;

import com.example.be.model.entity.SenderType;
import lombok.Data;

import java.util.UUID;

@Data
public class ChatMessageRequest {

    private UUID userId;

    private UUID sessionId;

    private String message;

    private SenderType senderType;
}

