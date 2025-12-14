package com.example.be.model.dto.facade.response;

import com.example.be.model.entity.SenderType;
import com.example.be.model.standard.BaseResponseDto;
import java.util.List;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ChatResponse extends BaseResponseDto {

    private UUID sessionId;

    private String sessionTitle;

    private String message;

    private List<String> urls;

    private SenderType senderType;
}

