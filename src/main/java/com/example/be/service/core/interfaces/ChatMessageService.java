package com.example.be.service.core.interfaces;

import com.example.be.model.dto.service.request.ChatMessageCreationRequest;
import com.example.be.model.dto.service.response.ChatMessageResponse;

public interface ChatMessageService {

    ChatMessageResponse createChatMessage(ChatMessageCreationRequest request);
}

