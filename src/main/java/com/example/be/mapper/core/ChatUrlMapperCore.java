package com.example.be.mapper.core;

import com.example.be.model.dto.service.request.ChatUrlCreationRequest;
import com.example.be.model.dto.service.response.ChatUrlResponse;
import com.example.be.model.entity.ChatUrl;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class ChatUrlMapperCore {
  @Mapping(source = "chatUrl.message.id", target = "messageId")
  public abstract ChatUrlResponse toResponse(ChatUrl chatUrl);

  public abstract ChatUrl toEntity(ChatUrlCreationRequest chatUrlCreationRequest);
}
