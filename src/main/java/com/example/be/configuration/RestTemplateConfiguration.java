package com.example.be.configuration;

import java.security.cert.X509Certificate;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfiguration {

  @Bean
  public RestTemplate restTemplate() {
    try {
      // Create trust manager that accepts all certificates
      TrustManager[] trustAllCerts = new TrustManager[]{
          new X509TrustManager() {
            @Override
            public X509Certificate[] getAcceptedIssuers() {
              return new X509Certificate[0];
            }

            @Override
            public void checkClientTrusted(X509Certificate[] certs, String authType) {
              // Trust all clients
            }

            @Override
            public void checkServerTrusted(X509Certificate[] certs, String authType) {
              // Trust all servers
            }
          }
      };

      // Install the all-trusting trust manager
      SSLContext sslContext = SSLContext.getInstance("TLS");
      sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

      // Create SSL socket factory that accepts all hostnames
      SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(
          sslContext,
          (hostname, session) -> true // Accept all hostnames
      );

      // Create HTTP client with custom SSL context
      CloseableHttpClient httpClient = HttpClients.custom()
          .setConnectionManager(
              PoolingHttpClientConnectionManagerBuilder.create()
                  .setSSLSocketFactory(socketFactory)
                  .build()
          )
          .build();

      // Create request factory
      HttpComponentsClientHttpRequestFactory factory =
          new HttpComponentsClientHttpRequestFactory(httpClient);

      return new RestTemplate(factory);

    } catch (Exception e) {
      throw new RuntimeException("Failed to create RestTemplate with SSL bypass", e);
    }
  }
}