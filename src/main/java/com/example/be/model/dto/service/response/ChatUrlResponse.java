package com.example.be.model.dto.service.response;

import com.example.be.model.entity.SenderType;
import com.example.be.model.standard.BaseResponseDto;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ChatUrlResponse extends BaseResponseDto {

    private UUID messageId;

    private String url;
}

