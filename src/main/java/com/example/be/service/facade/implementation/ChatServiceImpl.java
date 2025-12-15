package com.example.be.service.facade.implementation;

import com.example.be.client.dto.request.RagRequestDto;
import com.example.be.client.dto.response.RagResponseDto;
import com.example.be.client.llm.factory.LLMProviders;
import com.example.be.client.llm.factory.LLMServiceFactory;
import com.example.be.client.rag.factory.RagProviders;
import com.example.be.client.rag.factory.RagServiceFactory;
import com.example.be.configuration.YamlPropertySourceFactory;
import com.example.be.exception.RagQueryException;
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
import org.springframework.util.StopWatch;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@PropertySource(value = "classpath:prompt.yml", factory = YamlPropertySourceFactory.class)
public class ChatServiceImpl implements ChatService {

  private final ChatSessionService chatSessionService;
  private final ChatMessageService chatMessageService;

  private final ChatMapper chatMapper;

  private final LLMServiceFactory llmServiceFactory;
  private final RagServiceFactory ragServiceFactory;

  @Value("${chat-session.title-name-prompt}")
  private String chatSessionTitlePrompt;

  @Override
  @Transactional(rollbackFor = RagQueryException.class)
  public ChatResponse chat(ChatRequest chatRequest) {

    boolean isFirstMessage = Objects.isNull(chatRequest.getSessionId());
    ChatSessionResponse chatSessionResponse;

    if (isFirstMessage) {
      ChatSessionCreationRequest chatSessionCreationRequest = new ChatSessionCreationRequest();
      chatSessionCreationRequest.setTitle(getChatSessionTitle(chatRequest.getMessage()));
      chatSessionCreationRequest.setUserId(UUID.fromString(chatRequest.getUserId()));

      chatSessionResponse = chatSessionService.createChatSession(chatSessionCreationRequest);
    } else {
      chatSessionResponse = chatSessionService.findChatSessionById(chatRequest.getSessionId());
    }

    String sessionId = chatSessionResponse.getId().toString();

    ChatMessageResponse userMessage = chatMessageService.createChatMessage(
        ChatMessageCreationRequest.builder()
            .sessionId(sessionId)
            .message(chatRequest.getMessage())
            .senderType(chatRequest.getSenderType())
            .build());

    log.info("Created user chat message {}", userMessage.getId());

    RagResponseDto botAnswer = this.queryRag(chatRequest.getMessage());

    ChatMessageResponse botMessage = chatMessageService.createChatMessage(
        ChatMessageCreationRequest.builder().sessionId(sessionId).message(botAnswer.getMessage())
            .senderType(SenderType.AI).build());

    return this.buildChatResponse(botMessage, chatSessionResponse, botAnswer);
  }

  private ChatResponse buildChatResponse(ChatMessageResponse botChatMessageResponse,
      ChatSessionResponse chatSessionResponse, RagResponseDto ragResponseDto) {
    ChatResponse chatResponse = this.chatMapper.toChatResponse(botChatMessageResponse,
        chatSessionResponse);
    chatResponse.setUrls(ragResponseDto.getUrls());

    return chatResponse;
  }

  private RagResponseDto queryRag(String question) {

    StopWatch stopWatch = new StopWatch("ChatBot");

    try {
      stopWatch.start("UITHippoRAG retrieving");

      RagRequestDto ragRequestDto = RagRequestDto.builder().question(question).build();
      RagResponseDto ragResponseDto = this.ragServiceFactory.getService(RagProviders.UIT_HIPPORAG)
          .query(ragRequestDto);

      stopWatch.stop();

      return ragResponseDto;

    } catch (Exception exception) {
      log.error(exception.getMessage());
      log.error(Arrays.toString(exception.getStackTrace()));
      throw new RagQueryException(exception.getMessage());
    } finally {
      log.info(stopWatch.prettyPrint());
    }
  }

  private String getChatSessionTitle(String firstMessage) {
    try {
      return this.llmServiceFactory.getService(LLMProviders.OPENAI)
          .chat(chatSessionTitlePrompt.replace("{FIRST_MESSAGE}", firstMessage));
    } catch (Exception e) {
      log.error(Arrays.toString(e.getStackTrace()));
      throw e;
    }
  }
}
