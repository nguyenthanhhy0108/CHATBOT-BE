package com.example.be.model.dto.service.request;

import com.example.be.model.entity.SenderType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageCreationRequest {

    private String sessionId;

    private String message;

    private SenderType senderType;
}

