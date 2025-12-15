package com.example.be.client.rag.implementation;

import com.example.be.client.dto.request.RagRequestDto;
import com.example.be.client.dto.response.RagResponseDto;
import com.example.be.client.rag.RagService;
import com.example.be.client.rag.factory.RagProviders;
import java.util.Arrays;
import org.springframework.stereotype.Component;

@Component(RagProviders.UIT_HIPPORAG)
public class UitHippoRagServiceImpl implements RagService {

  @Override
  public RagResponseDto query(RagRequestDto ragRequestDto) {
    return RagResponseDto.builder()
        .message("Mock response from UitHippoRagServiceImpl")
        .urls(Arrays.asList(
            "https://example.com", "https://example.com", "https://example.com"
        ))
        .build();
  }
}
