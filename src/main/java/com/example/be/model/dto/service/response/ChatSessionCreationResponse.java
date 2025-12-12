package com.example.be.model.dto.service.response;

import com.example.be.model.standard.BaseResponseDto;

import java.util.UUID;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ChatSessionCreationResponse extends BaseResponseDto {

    private UUID userId;

    private String title;
}
