package com.example.be.client.rag.implementation;

import com.example.be.client.dto.request.RagRequestDto;
import com.example.be.client.dto.response.RagResponseDto;
import com.example.be.client.rag.RagService;
import com.example.be.client.rag.factory.RagProviders;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component(RagProviders.DUMMY_RAG)
public class DummyRagServiceImpl implements RagService {

  @Override
  public RagResponseDto query(RagRequestDto ragRequestDto) {
    return RagResponseDto.builder()
        .message("Mock answer from dummy service")
        .urls(List.of("https://example.com", "https://example.com", "https://example.com"))
        .build();
  }
}