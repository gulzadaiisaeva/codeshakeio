package com.example.codeshakeio.request;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.net.URI;

public interface GetRequest {

    WebClient.RequestHeadersSpec<?> webClientBuilder(URI uri, HttpHeaders headers);

    Flux subscribe(URI uri, HttpHeaders headers, Class clazz);

    <T> ResponseEntity<T> get(URI uri, HttpHeaders headers, TypeReference<T> responseType) throws Exception;

}

