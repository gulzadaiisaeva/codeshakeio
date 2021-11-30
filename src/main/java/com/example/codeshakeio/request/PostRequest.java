package com.example.codeshakeio.request;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;

public interface PostRequest {
    <D> WebClient.RequestHeadersSpec<?> webClientBuilder(URI uri, HttpHeaders headers, D json);

    <D> Mono subscribe(URI uri, HttpHeaders headers, D json, ParameterizedTypeReference<?> responseType);

    <D> Mono<ClientResponse> subscribe(URI uri, HttpHeaders headers, D json);

    <T> Object post(URI uri, HttpHeaders headers, Object json, ParameterizedTypeReference responseType) throws Exception;


}

