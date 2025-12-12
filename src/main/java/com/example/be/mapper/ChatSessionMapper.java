package com.example.be.mapper;

import org.mapstruct.Mapper;
import com.example.be.model.dto.service.response.ChatSessionCreationResponse;
import com.example.be.model.entity.ChatSession;

@Mapper(componentModel = "spring")
public interface ChatSessionMapper {

    ChatSessionCreationResponse toCreationResponse(ChatSession chatSession);
}
