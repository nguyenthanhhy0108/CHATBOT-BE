package com.example.be.mapper.core;

import org.mapstruct.Mapper;
import com.example.be.model.dto.service.response.ChatSessionResponse;
import com.example.be.model.entity.ChatSession;

@Mapper(componentModel = "spring")
public abstract class ChatSessionMapperCore {

    public abstract ChatSessionResponse toResponse(ChatSession chatSession);
}
