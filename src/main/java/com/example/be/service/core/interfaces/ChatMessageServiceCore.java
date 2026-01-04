package com.example.be.service.core.interfaces;

import com.example.be.model.dto.service.request.ChatMessageCreationRequest;
import com.example.be.model.dto.service.response.ChatMessageResponse;
import com.example.be.model.entity.ChatUrl;
import java.util.List;

public interface ChatMessageServiceCore {

  ChatMessageResponse createChatMessage(ChatMessageCreationRequest request);

  ChatMessageResponse createChatMessageWithChatUrls(ChatMessageCreationRequest request,
      List<ChatUrl> chatUrls);

  java.util.List<ChatMessageResponse> getMessagesBySessionId(String sessionId);
}

