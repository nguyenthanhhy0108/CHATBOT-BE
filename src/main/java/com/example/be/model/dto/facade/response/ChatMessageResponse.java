package com.example.be.model.dto.facade.response;

import com.example.be.model.entity.SenderType;
import com.example.be.model.standard.BaseResponseDto;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ChatMessageResponse extends BaseResponseDto {

    private UUID sessionId;

    private String message;

    private SenderType senderType;
}

