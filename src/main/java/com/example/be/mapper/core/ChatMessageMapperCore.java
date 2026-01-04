package com.example.be.mapper.core;

import com.example.be.model.dto.service.response.ChatMessageResponse;
import com.example.be.model.entity.ChatMessage;
import com.example.be.model.entity.ChatUrl;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class ChatMessageMapperCore {

    @Mapping(source = "chatMessage.session.id", target = "sessionId")
    @Mapping(source = "chatMessage.urls", target = "urls")
    public abstract ChatMessageResponse toResponse(ChatMessage chatMessage);

    protected String map(ChatUrl chatUrl) {
        return chatUrl.getUrl();
    }
}

