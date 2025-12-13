package com.example.be.service.facade.implementation;

import com.example.be.client.llm.factory.LLMServiceFactory;
import com.example.be.configuration.YamlPropertySourceFactory;
import com.example.be.mapper.ChatMapper;
import com.example.be.model.dto.facade.request.ChatMessageRequest;
import com.example.be.model.dto.facade.response.ChatMessageResponse;
import com.example.be.model.dto.service.request.ChatMessageCreationRequest;
import com.example.be.model.dto.service.request.ChatSessionCreationRequest;
import com.example.be.model.dto.service.response.ChatMessageCreationResponse;
import com.example.be.model.dto.service.response.ChatSessionCreationResponse;
import com.example.be.service.core.interfaces.ChatMessageService;
import com.example.be.service.core.interfaces.ChatSessionService;
import com.example.be.service.facade.interfaces.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Objects;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@PropertySource(value = "classpath:prompt.yml", factory = YamlPropertySourceFactory.class)
public class ChatServiceImpl implements ChatService {

  private static final String OPENAI_PROVIDER = "OPENAI";

  private final ChatSessionService chatSessionService;
  private final ChatMessageService chatMessageService;

  private final ChatMapper chatMapper;

  private final LLMServiceFactory llmServiceFactory;

  @Value("${chat-session.title-name-prompt}")
  private String chatSessionTitlePrompt;

  @Override
  @Transactional
  public ChatMessageResponse chat(ChatMessageRequest chatMessageRequest) {
    boolean isFirstMessage = Objects.isNull(chatMessageRequest.getSessionId());

    ChatSessionCreationRequest chatSessionCreationRequest = new ChatSessionCreationRequest();
    if (isFirstMessage) {
      chatSessionCreationRequest.setTitle(
          this.getChatSessionTitle(chatMessageRequest.getMessage())
      );
    }
    chatSessionCreationRequest.setUserId(chatMessageRequest.getUserId());
    ChatSessionCreationResponse chatSessionCreationResponse =
        this.chatSessionService.createChatSession(chatSessionCreationRequest);

    ChatMessageCreationRequest chatMessageCreationRequest =
        ChatMessageCreationRequest.builder()
            .sessionId(chatSessionCreationResponse.getId())
            .message(chatMessageRequest.getMessage())
            .senderType(chatMessageRequest.getSenderType())
            .build();

    ChatMessageCreationResponse chatMessageCreationResponse = this.chatMessageService.createChatMessage(
        chatMessageCreationRequest);

    return this.chatMapper
        .toChatMessageResponse(chatMessageCreationResponse, chatSessionCreationResponse);
  }

  private String getChatSessionTitle(String firstMessage) {
    try {
      return this.llmServiceFactory.getService(ChatServiceImpl.OPENAI_PROVIDER)
          .chat(chatSessionTitlePrompt.replace("{FIRST_MESSAGE}", firstMessage));
    } catch (Exception e) {
      log.error(Arrays.toString(e.getStackTrace()));
      throw e;
    }
  }
}
