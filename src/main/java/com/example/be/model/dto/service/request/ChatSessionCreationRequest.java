package com.example.be.model.dto.service.request;

import java.util.UUID;

import lombok.Data;

@Data
public class ChatSessionCreationRequest {

    private UUID userId;

    private String title;
}
