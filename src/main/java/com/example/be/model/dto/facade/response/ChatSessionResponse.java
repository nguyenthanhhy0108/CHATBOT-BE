package com.example.be.model.dto.facade.response;

import com.example.be.model.standard.BaseResponseDto;
import java.util.UUID;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ChatSessionResponse extends BaseResponseDto {

    private UUID userId;

    private String title;
}
