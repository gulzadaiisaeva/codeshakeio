package com.example.codeshakeio.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@ComponentScan(basePackageClasses = WebClient.Builder.class)
@RequiredArgsConstructor
@Slf4j
public class WebClientConfig {

    @Qualifier("serializingObjectMapper")
    private final ObjectMapper objectMapper;

    private WebClient.Builder createBaseWebClientBuilder() {
        log.info("Created base WebClientBuilder");
        return WebClient.builder()
                .exchangeStrategies(createExchangeStrategies());
    }

    private ExchangeStrategies createExchangeStrategies() {
        log.info("Created base ExchangeStrategies");
        return ExchangeStrategies.builder()
                .codecs(
                        configurer -> {
                            configurer.defaultCodecs().jackson2JsonDecoder(new Jackson2JsonDecoder(objectMapper));
                            configurer.defaultCodecs().jackson2JsonEncoder(new Jackson2JsonEncoder(objectMapper));
                        }
                )
                .build();
    }

}
