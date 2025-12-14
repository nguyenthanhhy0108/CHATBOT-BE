package com.example.be.service.core.implementation;

import com.example.be.mapper.ChatMessageMapper;
import com.example.be.model.dto.service.request.ChatMessageCreationRequest;
import com.example.be.model.dto.service.response.ChatMessageResponse;
import com.example.be.model.entity.ChatMessage;
import com.example.be.model.entity.ChatSession;
import com.example.be.repository.interfaces.ChatMessageRepository;
import com.example.be.repository.interfaces.ChatSessionRepository;
import com.example.be.service.core.interfaces.ChatMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ChatMessageServiceImpl implements ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatSessionRepository chatSessionRepository;
    private final ChatMessageMapper chatMessageMapper;

    @Override
    public ChatMessageResponse createChatMessage(ChatMessageCreationRequest request) {
        validateRequest(request);

        ChatSession session = chatSessionRepository.findById(request.getSessionId())
                .orElseThrow(() -> new IllegalArgumentException("Chat session not found: " + request.getSessionId()));

        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setSession(session);
        chatMessage.setMessage(request.getMessage());
        chatMessage.setSenderType(request.getSenderType());

        try {
            ChatMessage savedMessage = chatMessageRepository.save(chatMessage);
            return chatMessageMapper.toResponse(savedMessage);
        } catch (Exception e) {
            log.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

    private void validateRequest(ChatMessageCreationRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }
        UUID sessionId = request.getSessionId();
        if (sessionId == null) {
            throw new IllegalArgumentException("sessionId is required to create a chat message");
        }
        if (request.getSenderType() == null) {
            throw new IllegalArgumentException("senderType is required to create a chat message");
        }
        if (request.getMessage() == null || request.getMessage().isBlank()) {
            throw new IllegalArgumentException("message is required to create a chat message");
        }
    }
}

