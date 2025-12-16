package com.example.be.mapper.core;

import com.example.be.model.dto.service.response.ChatMessageResponse;
import com.example.be.model.entity.ChatMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class ChatMessageMapperCore {

    @Mapping(source = "chatMessage.session.id", target = "sessionId")
    public abstract ChatMessageResponse toResponse(ChatMessage chatMessage);
}

