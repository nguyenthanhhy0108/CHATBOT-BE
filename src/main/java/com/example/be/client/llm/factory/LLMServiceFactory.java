package com.example.be.client.llm.factory;

import com.example.be.client.llm.LLMService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class LLMServiceFactory {

    private final Map<String, LLMService> serviceMap;

    public LLMService getService(String provider) {
        if (provider == null) {
            return defaultService();
        }

        LLMService service = serviceMap.get(provider.toUpperCase());

        if (service == null) {
            throw new IllegalArgumentException(
                "Unsupported LLM provider: " + provider
            );
        }

        return service;
    }

    public LLMService defaultService() {
        return serviceMap.get(LLMProviders.OPENAI);
    }

    public Set<String> getSupportedProviders() {
        return serviceMap.keySet();
    }
}