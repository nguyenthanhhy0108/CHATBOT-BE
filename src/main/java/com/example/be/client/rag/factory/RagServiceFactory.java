package com.example.be.client.rag.factory;

import com.example.be.client.llm.LLMService;
import com.example.be.client.rag.RagService;
import com.example.be.client.rag.implementation.UitHippoRagServiceImpl;
import java.util.Map;
import java.util.Set;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class RagServiceFactory {

  private final Map<String, RagService> serviceMap;

  public RagService getService(String provider) {
    if (provider == null) {
      return defaultService();
    }

    RagService service = serviceMap.get(provider.toUpperCase());

    if (service == null) {
      throw new IllegalArgumentException(
          "Unsupported RAG provider: " + provider
      );
    }

    return service;
  }

  public RagService defaultService() {
    return serviceMap.get(RagProviders.UIT_HIPPORAG);
  }

  public Set<String> getSupportedProviders() {
    return serviceMap.keySet();
  }

}
