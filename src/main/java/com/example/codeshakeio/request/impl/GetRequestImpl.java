package com.example.codeshakeio.request.impl;

import com.example.codeshakeio.request.CommonRequestResponseService;
import com.example.codeshakeio.request.GetRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
public class GetRequestImpl implements GetRequest {
    //WebClient Non-Blocking Client
    //WebClient detaylarına aşağıdaki adresten bak
    //https://docs.spring.io/spring/docs/5.0.16.RELEASE/spring-framework-reference/web-reactive.html

    private final CommonRequestResponseService commonRequestResponseService;
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
    public <T> ResponseEntity<T> get(URI uri,
                                     HttpHeaders headers,
                                     TypeReference<T> responseType) throws Exception {
        ResponseEntity<T> response = null;
        ResponseEntity<String> responseString = null;

        try {
            HttpEntity<String> httpEntity = new HttpEntity<>(uri.toString(), headers);
            responseString = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, String.class);

            log.debug("GetRequest Response status: {} - body: {}", responseString.getStatusCode(), responseString.getBody());

            response = commonRequestResponseService.getResponseByType(responseType, responseString);

        } catch (HttpStatusCodeException e) {
            response = (ResponseEntity<T>) ResponseEntity.status(e.getRawStatusCode()).headers(e.getResponseHeaders())
                    .body(e.getResponseBodyAsString());
        } catch (Exception e) {
            log.error("END with ERROR");
            throw new Exception(e);
        }
        return response;
    }

}
