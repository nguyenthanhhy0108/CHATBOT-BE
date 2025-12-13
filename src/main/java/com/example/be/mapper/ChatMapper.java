package com.example.be.mapper;

import com.example.be.model.dto.facade.response.ChatMessageResponse;
import com.example.be.model.dto.service.response.ChatMessageCreationResponse;
import com.example.be.model.dto.service.response.ChatSessionCreationResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class ChatMapper {

  public ChatMessageResponse toChatMessageResponse(
      ChatMessageCreationResponse chatMessageCreationResponse,
      ChatSessionCreationResponse chatSessionCreationResponse) {
    return ChatMessageResponse.builder()
        .sessionId(chatSessionCreationResponse.getId())
        .id(chatMessageCreationResponse.getId())
        .senderType(chatMessageCreationResponse.getSenderType())
        .message(chatMessageCreationResponse.getMessage())
        .createdAt(chatMessageCreationResponse.getCreatedAt())
        .createdBy(chatMessageCreationResponse.getCreatedBy())
        .updatedAt(chatMessageCreationResponse.getUpdatedAt())
        .updatedBy(chatMessageCreationResponse.getUpdatedBy())
        .version(chatMessageCreationResponse.getVersion())
        .isDeleted(chatMessageCreationResponse.isDeleted())
        .build();
  }

}
