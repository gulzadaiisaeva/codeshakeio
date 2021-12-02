package com.example.codeshakeio.request.impl;


import com.example.codeshakeio.request.PostRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostRequestImpl implements PostRequest {


    private final RestTemplate restTemplate;

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
    public <T> Object post(URI uri,
                           HttpHeaders headers,
                           Object json,
                           ParameterizedTypeReference responseType
    ) throws Exception {
        ResponseEntity<T> response = null;

        try {
            HttpEntity<Object> entity;

            if (null != json) {
                entity = new HttpEntity<>(json, headers);
            } else {
                entity = new HttpEntity<>(uri.toString(), headers);
            }
            response = restTemplate.exchange(uri, HttpMethod.POST, entity, responseType);

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
