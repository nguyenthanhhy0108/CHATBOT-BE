package com.example.be.model.dto.facade.response;

import com.example.be.model.entity.SenderType;
import com.example.be.model.standard.BaseResponseDto;
import java.util.List;
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
public class ChatMessageHistoryItem extends BaseResponseDto {

  private UUID sessionId;

  private String message;

  private SenderType senderType;

  private List<String> urls;
}


