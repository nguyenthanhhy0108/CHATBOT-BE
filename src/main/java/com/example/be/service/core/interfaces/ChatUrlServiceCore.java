package com.example.be.service.core.interfaces;

import com.example.be.model.dto.service.request.ChatUrlCreationRequest;
import com.example.be.model.dto.service.response.ChatUrlResponse;
import com.example.be.model.entity.ChatMessage;
import com.example.be.model.entity.ChatUrl;
import java.util.List;

public interface ChatUrlServiceCore {
  List<ChatUrl> createChatUrlEntity(List<ChatUrlCreationRequest> request);
}
