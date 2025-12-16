package com.example.be.service.facade.implementation;

import com.example.be.helper.SecurityHelper;
import com.example.be.mapper.facade.ChatSessionMapper;
import com.example.be.model.dto.facade.response.ChatSessionResponse;
import com.example.be.service.core.interfaces.ChatSessionServiceCore;
import com.example.be.service.facade.interfaces.ChatSessionService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ChatSessionServiceImpl implements ChatSessionService {

  private final ChatSessionServiceCore chatSessionServiceCore;
  private final ChatSessionMapper chatSessionMapper;
  private final SecurityHelper securityHelper;

  @Override
  @PreAuthorize("@security.hasUserId()")
  public List<ChatSessionResponse> getAllChatSessionsOfUser() {

    String userId = securityHelper.getUserId().toString();

    return chatSessionServiceCore
        .getAllChatSessionsOfUser(userId)
        .stream()
        .map(chatSessionMapper::toChatSessionResponse)
        .collect(Collectors.toList());
  }
}
