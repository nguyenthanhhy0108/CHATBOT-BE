package com.example.be.client.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OpenAiRequestDto {

    @Builder.Default
    private String model = "gpt-4o-mini";

    private List<Message> messages;

    @Builder.Default
    private Double temperature = 0.7;

    @JsonProperty("max_tokens")
    @Builder.Default
    private Integer maxTokens = 256;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Message {
        private String role;
        private String content;
    }
}
