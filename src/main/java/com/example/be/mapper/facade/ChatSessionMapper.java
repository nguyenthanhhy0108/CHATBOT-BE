package com.example.be.mapper.facade;

import com.example.be.model.dto.facade.response.ChatSessionResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class ChatSessionMapper {

  public abstract ChatSessionResponse toChatSessionResponse(final
      com.example.be.model.dto.service.response.ChatSessionResponse chatSessionResponse);

}
