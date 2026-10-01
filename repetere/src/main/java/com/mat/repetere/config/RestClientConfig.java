package com.mat.repetere.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.nio.charset.StandardCharsets;

@Configuration
public class RestClientConfig {
    @Value("${tts.service.base-url}")
    private String ttsBaseUrl;

    @Bean
    public RestClient ttsRestClient() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(5000);  // 5 segundos para conectar
        requestFactory.setReadTimeout(60000);    // 60 segundos esperando el procesamiento de Python

        return RestClient.builder()
                .baseUrl("http://localhost:8000/api/v1")
                .requestFactory(requestFactory)
                .build();
    }
}
