package com.example.be.mapper;

import com.example.be.model.dto.service.response.ChatMessageCreationResponse;
import com.example.be.model.entity.ChatMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ChatMessageMapper {

    ChatMessageCreationResponse toCreationResponse(ChatMessage chatMessage);
}

