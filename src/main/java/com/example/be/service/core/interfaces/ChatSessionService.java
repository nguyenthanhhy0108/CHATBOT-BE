package com.example.be.service.core.interfaces;

import com.example.be.model.dto.service.request.ChatSessionCreationRequest;
import com.example.be.model.dto.service.response.ChatSessionResponse;

public interface ChatSessionService {

    ChatSessionResponse createChatSession(ChatSessionCreationRequest request);

    ChatSessionResponse findChatSessionById(String id);

}
