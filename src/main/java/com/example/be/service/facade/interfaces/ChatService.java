package com.example.be.service.facade.interfaces;

import com.example.be.model.dto.facade.request.ChatRequest;
import com.example.be.model.dto.facade.response.ChatMessageHistoryItem;
import com.example.be.model.dto.facade.response.ChatResponse;

public interface ChatService {

    ChatResponse chat(ChatRequest chatRequest);

    java.util.List<ChatMessageHistoryItem> getChatHistory(String sessionId);
}
