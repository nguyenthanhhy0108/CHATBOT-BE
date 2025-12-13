package com.example.be.mapper;

import com.example.be.model.dto.service.response.ChatMessageCreationResponse;
import com.example.be.model.entity.ChatMessage;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class ChatMessageMapper {

    public abstract ChatMessageCreationResponse toCreationResponse(ChatMessage chatMessage);
}

