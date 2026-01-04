package com.example.be.client.rag.implementation;

import com.example.be.client.dto.request.RagRequestDto;
import com.example.be.client.dto.response.RagResponseDto;
import com.example.be.client.rag.RagService;
import com.example.be.client.rag.factory.RagProviders;
import com.example.be.configuration.YamlPropertySourceFactory;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component(RagProviders.UIT_HIPPORAG)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@PropertySource(value = "classpath:ai-service.yml", factory = YamlPropertySourceFactory.class)
public class UitHippoRagServiceImpl implements RagService {

  private final RestTemplate restTemplate;

  @Value("${rag.hippo.api.url}")
  private String hippoRagApiUrl;

  @Override
  public RagResponseDto query(RagRequestDto ragRequestDto) {
    try {
      // Prepare request
      HippoRagRequest request = new HippoRagRequest(ragRequestDto.getQuestion());

      // Set headers
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_JSON);

      HttpEntity<HippoRagRequest> entity = new HttpEntity<>(request, headers);

      // Call API
      ResponseEntity<HippoRagResponse> response = restTemplate.postForEntity(hippoRagApiUrl, entity,
          HippoRagResponse.class);

      // Process response
      if (response.getBody() != null && response.getBody().getData() != null) {
        HippoRagData data = response.getBody().getData();

        List<String> urls =
            data.getEvidences() != null ? data.getEvidences().stream().map(Evidence::getUrl)
                .distinct().collect(Collectors.toList()) : Collections.emptyList();

        return RagResponseDto.builder().message(data.getAnswer().replaceAll("<br\\s*/?>", ""))
            .urls(urls).build();
      }

      log.warn("Empty response from HippoRAG API");
      return RagResponseDto.builder().message("No response from RAG service")
          .urls(Collections.emptyList()).build();

    } catch (Exception e) {
      log.error("Error calling HippoRAG API", e);
      return RagResponseDto.builder().message("Error: " + e.getMessage())
          .urls(Collections.emptyList()).build();
    }
  }

  // Inner classes for API mapping
  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  private static class HippoRagRequest {

    private String text;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  private static class HippoRagResponse {

    private HippoRagData data;
    private String message;

    @JsonProperty("status_code")
    private Integer statusCode;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  private static class HippoRagData {

    private String answer;
    private List<Evidence> evidences;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  private static class Evidence {

    @JsonProperty("_id")
    private Integer id;

    @JsonProperty("_index")
    private Integer index;

    @JsonProperty("chunk_id")
    private String chunkId;

    private String url;

    @JsonProperty("vi_chunk")
    private String viChunk;
  }
}