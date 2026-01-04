package com.example.be.model.dto.service.request;

import com.example.be.model.entity.SenderType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatUrlCreationRequest {

    private String url;
}

