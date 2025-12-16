package com.example.be.service.core.interfaces;

import com.example.be.model.dto.service.request.ChatSessionCreationRequest;
import com.example.be.model.dto.service.response.ChatSessionResponse;
import java.util.List;

public interface ChatSessionServiceCore {

    ChatSessionResponse createChatSession(ChatSessionCreationRequest request);

    ChatSessionResponse findChatSessionById(String id);

    List<ChatSessionResponse> getAllChatSessionsOfUser(String userId);

}
