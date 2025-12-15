package com.example.be.mapper;

import com.example.be.model.dto.service.response.ChatMessageResponse;
import com.example.be.model.entity.ChatMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class ChatMessageMapper {

    @Mapping(source = "chatMessage.session.id", target = "sessionId")
    public abstract ChatMessageResponse toResponse(ChatMessage chatMessage);
}

