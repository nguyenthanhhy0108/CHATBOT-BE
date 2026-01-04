package com.example.be.service.core.implementation;

import com.example.be.mapper.core.ChatMessageMapperCore;
import com.example.be.model.dto.service.request.ChatMessageCreationRequest;
import com.example.be.model.dto.service.response.ChatMessageResponse;
import com.example.be.model.entity.ChatMessage;
import com.example.be.model.entity.ChatSession;
import com.example.be.model.entity.ChatUrl;
import com.example.be.repository.interfaces.ChatMessageRepository;
import com.example.be.repository.interfaces.ChatSessionRepository;
import com.example.be.service.core.interfaces.ChatMessageServiceCore;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.UUID;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ChatMessageServiceCoreImpl implements ChatMessageServiceCore {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatSessionRepository chatSessionRepository;
    private final ChatMessageMapperCore chatMessageMapperCore;

    private ChatMessage buildBasicInfo(ChatMessageCreationRequest request) {
        ChatSession session = chatSessionRepository.findById(UUID.fromString(request.getSessionId()))
            .orElseThrow(() -> new IllegalArgumentException("Chat session not found: " + request.getSessionId()));

        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setSession(session);
        chatMessage.setMessage(request.getMessage());
        chatMessage.setSenderType(request.getSenderType());

        return chatMessage;
    }

    @Override
    @Transactional
    public ChatMessageResponse createChatMessage(ChatMessageCreationRequest request) {
        ChatMessage chatMessage = buildBasicInfo(request);

        try {
            ChatMessage savedMessage = chatMessageRepository.save(chatMessage);
            return chatMessageMapperCore.toResponse(savedMessage);
        } catch (Exception e) {
            log.error(Arrays.toString(e.getStackTrace()));
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public java.util.List<ChatMessageResponse> getMessagesBySessionId(String sessionId) {
        java.util.UUID sessionUuid = java.util.UUID.fromString(sessionId);

        java.util.List<ChatMessage> messages =
            chatMessageRepository.findBySession_IdOrderByCreatedAtAsc(sessionUuid);

        return messages.stream()
            .map(chatMessageMapperCore::toResponse)
            .toList();
    }

    @Override
    @Transactional
    public ChatMessageResponse createChatMessageWithChatUrls(
        ChatMessageCreationRequest request,
        List<ChatUrl> chatUrls
    ) {
        ChatMessage chatMessage = buildBasicInfo(request);

        if (chatUrls != null && !chatUrls.isEmpty()) {
            chatMessage.setUrls(new ArrayList<>(chatUrls));
            for (ChatUrl chatUrl : chatUrls) {
                chatUrl.setMessage(chatMessage);
            }
        }

        ChatMessage savedMessage = chatMessageRepository.save(chatMessage);

        return chatMessageMapperCore.toResponse(savedMessage);
    }
}

