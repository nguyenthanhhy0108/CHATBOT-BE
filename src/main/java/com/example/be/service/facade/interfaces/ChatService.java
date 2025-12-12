package com.example.be.service.facade.interfaces;

import com.example.be.model.dto.facade.request.ChatMessageRequest;
import com.example.be.model.dto.facade.response.ChatMessageResponse;

public interface ChatService {

    ChatMessageResponse chat(ChatMessageRequest chatMessageRequest);
}
