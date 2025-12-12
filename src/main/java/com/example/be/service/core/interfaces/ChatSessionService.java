package com.example.be.service.core.interfaces;

import com.example.be.model.dto.service.request.ChatSessionCreationRequest;
import com.example.be.model.dto.service.response.ChatSessionCreationResponse;

public interface ChatSessionService {

    ChatSessionCreationResponse createChatSession(ChatSessionCreationRequest request);
}
