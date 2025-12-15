package com.example.be.client.rag;

import com.example.be.client.dto.request.RagRequestDto;
import com.example.be.client.dto.response.RagResponseDto;

public interface RagService {
  RagResponseDto query(RagRequestDto ragRequestDto);
}
