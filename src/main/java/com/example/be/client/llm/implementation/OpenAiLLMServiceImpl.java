package com.example.be.client.llm.implementation;

import com.example.be.client.dto.request.OpenAiRequestDto;
import com.example.be.client.dto.response.OpenAiResponseDto;
import com.example.be.client.llm.LLMService;
import com.example.be.configuration.YamlPropertySourceFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@PropertySource(value = "classpath:openai.yml", factory = YamlPropertySourceFactory.class)
public class OpenAiLLMServiceImpl implements LLMService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${openai.api-key}")
    private String apiKey;

    @Value("${openai.base-url}")
    private String baseUrl;

    @Override
    public String chat(String question) {
        if (question == null || question.isBlank()) {
            throw new IllegalArgumentException("Question must not be empty");
        }
        ensureApiKeyPresent();

        OpenAiRequestDto requestBody = OpenAiRequestDto.builder()
                .messages(List.of(
                        OpenAiRequestDto.Message.builder()
                                .role("user")
                                .content(question)
                                .build()
                ))
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        try {
            OpenAiResponseDto response = restTemplate.exchange(
                    baseUrl + "/chat/completions",
                    HttpMethod.POST,
                    new HttpEntity<>(requestBody, headers),
                    OpenAiResponseDto.class
            ).getBody();

            return response != null ? response.firstMessageContent() : null;
        } catch (Exception e) {
            log.error("Failed to call OpenAI: {}", e.getMessage(), e);
            throw e;
        }
    }

    private void ensureApiKeyPresent() {
        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException("OpenAI API key is not configured. Set llm.openai.api-key");
        }
    }
}
