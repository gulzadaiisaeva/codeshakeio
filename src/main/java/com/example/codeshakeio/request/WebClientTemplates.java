package com.example.codeshakeio.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.function.Function;

@Slf4j
@Service
@RequiredArgsConstructor
public class WebClientTemplates {


    /**
     * Verilen url e  post isteği atar ve cevabı bir mono objesi içinde döndürür
     *
     * @param builder                    WebClient Builder
     * @param requestUri                 istek atılacak endpoint
     * @param httpHeaders                Eklenecek Headerlar
     * @param parameterizedTypeReference response type referansı
     * @param body                       request body
     * @param <T>                        response type
     * @param <V>                        gönderilen body tipi
     * @return TypeReference ile verilen tipin Mono nesnesi
     */
    public <T, V> Mono<T> post(WebClient.Builder builder, String requestUri, HttpHeaders httpHeaders, ParameterizedTypeReference<T> parameterizedTypeReference, V body, Function<ClientResponse, Mono<? extends Throwable>> exceptionFunction) {

        LocalDateTime requestTime = LocalDateTime.now();
        log.info("Sending post request to: {}", requestUri);

        Mono<T> res = builder
                .build()
                .post()
                .uri(requestUri)
                .headers(header -> header.addAll(Optional.ofNullable(httpHeaders).orElse(new HttpHeaders())))
                .body(Mono.just(body), body.getClass())
                .retrieve()
                .onStatus(
                        HttpStatus::isError,
                        exceptionFunction
                )
                .bodyToMono(parameterizedTypeReference);

        return res;
    }


    /**
     * Verilen url e  put isteği atar ve cevabı bir mono objesi içinde döndürür
     *
     * @param builder                    WebClient Builder
     * @param requestUri                 istek atılacak endpoint
     * @param httpHeaders                Eklenecek Headerlar
     * @param parameterizedTypeReference response type referansı
     * @param body                       request body
     * @param <T>                        response type
     * @param <V>                        gönderilen body tipi
     * @return TypeReference ile verilen tipin Mono nesnesi
     */
    public <T, V> Mono<T> put(WebClient.Builder builder, String requestUri, HttpHeaders httpHeaders, ParameterizedTypeReference<T> parameterizedTypeReference, V body, Function<ClientResponse, Mono<? extends Throwable>> exceptionFunction) {
        LocalDateTime requestTime = LocalDateTime.now();
        log.info("Sending put request to: {}", requestUri);

        Mono<T> res = builder
                .build()
                .put()
                .uri(requestUri)
                .headers(header -> header.addAll(Optional.ofNullable(httpHeaders).orElse(new HttpHeaders())))
                .body(Mono.just(body), body.getClass())
                .retrieve()
                .onStatus(
                        HttpStatus::isError,
                        exceptionFunction
                )
                .bodyToMono(parameterizedTypeReference);

        return res;
    }

    /**
     * Verilen url e get isteği atar ve cevabı bir utility objesi içinde döndürür
     *
     * @param builder     WebClient Builder
     * @param requestUri  istek atılacak endpoint
     * @param httpHeaders Eklenecek Headerlar
     * @param <T>         response type
     * @return TypeReference ile verilen tipin Mono nesnesi
     */
    public <T> Mono<T> get(WebClient.Builder builder, String requestUri, HttpHeaders httpHeaders, ParameterizedTypeReference<T> parameterizedTypeReference, Function<ClientResponse, Mono<? extends Throwable>> exceptionFunction) {
        LocalDateTime requestTime = LocalDateTime.now();
        log.info("Sending post request to: {}", requestUri);

        Mono<T> res = builder
                .build()
                .get()
                .uri(requestUri)
                .headers(header -> header.addAll(Optional.ofNullable(httpHeaders).orElse(new HttpHeaders())))
                .retrieve()
                .onStatus(
                        HttpStatus::isError,
                        exceptionFunction
                )
                .bodyToMono(parameterizedTypeReference);

        return res;
    }

}
