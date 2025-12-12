package com.example.be.client.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OpenAiResponseDto {

    private List<Choice> choices;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Choice {
        private Message message;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Message {
        private String role;
        private String content;
    }

    public String firstMessageContent() {
        if (choices == null || choices.isEmpty()) return null;
        Message message = choices.get(0).getMessage();
        return message != null ? message.getContent() : null;
    }
}
