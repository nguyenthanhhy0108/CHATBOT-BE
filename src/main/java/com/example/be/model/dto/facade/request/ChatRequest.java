package com.example.be.model.dto.facade.request;

import com.example.be.model.entity.SenderType;
import lombok.Data;

import java.util.UUID;

@Data
public class ChatRequest {

    private String userId;

    private String sessionId;

    private String message;

    private SenderType senderType;
}

