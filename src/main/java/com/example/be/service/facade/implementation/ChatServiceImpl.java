package com.example.be.service.facade.implementation;

import com.example.be.client.llm.factory.LLMServiceFactory;
import com.example.be.configuration.YamlPropertySourceFactory;
import com.example.be.mapper.ChatMapper;
import com.example.be.model.dto.facade.request.ChatRequest;
import com.example.be.model.dto.facade.response.ChatResponse;
import com.example.be.model.dto.service.request.ChatMessageCreationRequest;
import com.example.be.model.dto.service.request.ChatSessionCreationRequest;
import com.example.be.model.dto.service.response.ChatMessageResponse;
import com.example.be.model.dto.service.response.ChatSessionResponse;
import com.example.be.model.entity.SenderType;
import com.example.be.service.core.interfaces.ChatMessageService;
import com.example.be.service.core.interfaces.ChatSessionService;
import com.example.be.service.facade.interfaces.ChatService;
import java.util.UUID;
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
  public ChatResponse chat(ChatRequest chatRequest) {
    boolean isFirstMessage = Objects.isNull(chatRequest.getSessionId());
    ChatSessionResponse chatSessionResponse;

    ChatSessionCreationRequest chatSessionCreationRequest = new ChatSessionCreationRequest();
    if (isFirstMessage) {
      chatSessionCreationRequest.setTitle(
          this.getChatSessionTitle(chatRequest.getMessage())
      );
      chatSessionCreationRequest.setUserId(UUID.fromString(chatRequest.getUserId()));
      chatSessionResponse =
          this.chatSessionService.createChatSession(chatSessionCreationRequest);
    } else {
      chatSessionResponse = this.chatSessionService.findChatSessionById(chatRequest.getSessionId());
    }

    ChatMessageCreationRequest chatMessageCreationRequest =
        ChatMessageCreationRequest.builder()
            .sessionId(chatSessionResponse.getId())
            .message(chatRequest.getMessage())
            .senderType(chatRequest.getSenderType())
            .build();

    ChatMessageResponse chatMessageResponse = this.chatMessageService.createChatMessage(
        chatMessageCreationRequest);

    //TODO: AI ChatMessageResponse

    ChatResponse chatResponse = this.chatMapper
        .toChatResponse(chatMessageResponse, chatSessionResponse);

    chatResponse.setSenderType(SenderType.AI);
    chatResponse.setUrls(Arrays.asList(
        "https://example.com", "https://example.com", "https://example.com"
    ));

    return chatResponse;
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
