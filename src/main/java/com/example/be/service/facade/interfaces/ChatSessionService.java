package com.example.be.service.facade.interfaces;

import com.example.be.model.dto.facade.request.ChatSessionCreateRequest;
import com.example.be.model.dto.facade.response.ChatSessionResponse;
import java.util.List;

public interface ChatSessionService {

  List<ChatSessionResponse> getAllChatSessionsOfUser();

  ChatSessionResponse createChatSession(ChatSessionCreateRequest request);

}
