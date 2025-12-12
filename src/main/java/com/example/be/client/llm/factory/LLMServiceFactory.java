package com.example.be.client.llm.factory;

import com.example.be.client.llm.LLMService;
import com.example.be.client.llm.implementation.OpenAiLLMServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LLMServiceFactory {

    public static final String OPENAI = "OPENAI";

    private final OpenAiLLMServiceImpl openAiLLMService;

    public LLMService getService(String provider) {
        return Optional.ofNullable(provider)
                .map(String::toUpperCase)
                .flatMap(p -> serviceMap().entrySet().stream()
                        .filter(e -> e.getKey().equals(p))
                        .map(Map.Entry::getValue)
                        .findFirst())
                .orElseThrow(() -> new IllegalArgumentException("Unsupported LLM provider: " + provider));
    }

    public LLMService defaultService() {
        return openAiLLMService;
    }

    private Map<String, LLMService> serviceMap() {
        return Map.of(OPENAI, openAiLLMService);
    }

    public Set<String> getSupportedProviders() {
        return this.serviceMap().keySet();
    }
}
