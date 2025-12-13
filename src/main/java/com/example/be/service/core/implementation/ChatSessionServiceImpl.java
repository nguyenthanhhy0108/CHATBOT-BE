package com.example.be.service.core.implementation;

import com.example.be.client.llm.factory.LLMServiceFactory;
import com.example.be.mapper.ChatSessionMapper;
import com.example.be.model.dto.service.request.ChatSessionCreationRequest;
import com.example.be.model.dto.service.response.ChatSessionCreationResponse;
import com.example.be.model.entity.ChatSession;
import com.example.be.repository.interfaces.ChatSessionRepository;
import com.example.be.service.core.interfaces.ChatSessionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ChatSessionServiceImpl implements ChatSessionService {

    private final LLMServiceFactory llmServiceFactory;

    private final ChatSessionRepository chatSessionRepository;
    private final ChatSessionMapper chatSessionMapper;

    @Override
    public ChatSessionCreationResponse createChatSession(ChatSessionCreationRequest request) {
        if (request.getUserId() == null) {
            throw new IllegalArgumentException("userId is required to create a chat session");
        }

        ChatSession session = ChatSession.builder()
                .userId(request.getUserId())
                .title(request.getTitle())
                .build();

        try {
            ChatSession savedSession = chatSessionRepository.save(session);
            return chatSessionMapper.toCreationResponse(savedSession);
        } catch (Exception e) {
            log.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }
}
