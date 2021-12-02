package com.example.codeshakeio.request.impl;

import com.example.codeshakeio.request.CommonRequestResponseService;
import com.example.codeshakeio.request.DeleteRequest;
import com.example.codeshakeio.request.GetRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class DeleteRequestImpl implements DeleteRequest {
    //WebClient Non-Blocking Client
    //WebClient detaylarına aşağıdaki adresten bak
    //https://docs.spring.io/spring/docs/5.0.16.RELEASE/spring-framework-reference/web-reactive.html

    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * Sync request atmayı sağlar.
     * request cevabı gelene kadar metod son bulmaz.
     * gerekli loglama işlemleri yapılmıştır
     * block olarak çalışşsada doFirst, doOnSuccess, doOnError, doFinally vb metodlar ile desteklenebilir
     *
     * @param uri     istek atılacak olan adress
     * @param headers eklenmek istenen header içerikleri.
     * @param <T>     metodun return obje tip belirlemesi
     * @return <T> Object requestin gerçek değerini döner
     * @throws Exception
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T> Object delete(URI uri,
                           HttpHeaders headers,
                           Object json,
                           ParameterizedTypeReference responseType
    ) throws Exception {
        ResponseEntity<T> response = null;

        try {
            HttpEntity<Object> entity;

            if(null !=json){
                entity = new HttpEntity<>(json, headers);
            }
            else{
                entity = new HttpEntity<>(uri.toString(), headers);
            }
            response = restTemplate.exchange(uri, HttpMethod.DELETE, entity, responseType);

        } catch (HttpStatusCodeException e) {
            log.error(e.getResponseBodyAsString());
            response = (ResponseEntity<T>) ResponseEntity.status(e.getRawStatusCode()).headers(e.getResponseHeaders())
                    .body(e.getResponseBodyAsString());
        } catch (Exception e) {
            throw new Exception(e);
        }
        return response;
    }
}
