package com.example.be.mapper;

import com.example.be.model.dto.facade.response.ChatResponse;
import com.example.be.model.dto.service.response.ChatMessageResponse;
import com.example.be.model.dto.service.response.ChatSessionResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class ChatMapper {

  public ChatResponse toChatResponse(
      ChatMessageResponse chatMessageResponse,
      ChatSessionResponse chatSessionResponse) {
    return ChatResponse.builder()
        .sessionId(chatSessionResponse.getId())
        .sessionTitle(chatSessionResponse.getTitle())
        .id(chatMessageResponse.getId())
        .senderType(chatMessageResponse.getSenderType())
        .message(chatMessageResponse.getMessage())
        .createdAt(chatMessageResponse.getCreatedAt())
        .createdBy(chatMessageResponse.getCreatedBy())
        .updatedAt(chatMessageResponse.getUpdatedAt())
        .updatedBy(chatMessageResponse.getUpdatedBy())
        .version(chatMessageResponse.getVersion())
        .isDeleted(chatMessageResponse.isDeleted())
        .build();
  }

}
