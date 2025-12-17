package com.example.be.service.core.interfaces;

import com.example.be.model.dto.service.request.ChatMessageCreationRequest;
import com.example.be.model.dto.service.response.ChatMessageResponse;

public interface ChatMessageServiceCore {

    ChatMessageResponse createChatMessage(ChatMessageCreationRequest request);

    java.util.List<ChatMessageResponse> getMessagesBySessionId(String sessionId);
}

