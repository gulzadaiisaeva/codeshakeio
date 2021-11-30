package com.example.codeshakeio.request.impl;


import com.example.codeshakeio.request.PostRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostRequestImpl implements PostRequest {
    //WebClient Non-Blocking Client
    //WebClient detaylarına aşağıdaki adresten bak
    //https://docs.spring.io/spring/docs/5.0.16.RELEASE/spring-framework-reference/web-reactive.html

    private final RestTemplate restTemplate;

    /**
     * Diğer metodlara builder olarak çalışır
     *
     * @param uri     istek atılacak olan adress
     * @param headers eklenmek istenen header içerikleri.
     * @return
     * @throws Exception
     */
    @Override
    public <D> WebClient.RequestHeadersSpec<?> webClientBuilder(URI uri, HttpHeaders headers, D json) {

        return WebClient.create()
                .post()
                .uri(String.valueOf(uri))
                .headers(httpHeaders -> httpHeaders.addAll(Optional.ofNullable(headers).orElse(new HttpHeaders())))
                //.body(json, json.getClass());
                .bodyValue(json);
    }

    /**
     * Async request atmayı sağlar
     * istek gönderilir ve cevap gelmeden metod biter. İstek geldiğinde cevabı dinlemek için return değerine subscribe olmak gerekir.
     * örnek kullanımı demoUsage içerisinde mevcut
     * onStatus metodları ile istenmeyen status dönüşlerinde exception handle edilir ve istenilen sonuç dönülebilir
     * onStatus burada başlangıç olarak kullanılmıştır ve sadece RuntimeException fırlatılmıştır
     * dönen cevabın handle edilip uygun dönüşün sağlanması gerekir
     * NOT: gerekli loglama işlemleri yapılmamıştır Subscribe olunan metod ile birlikte logları sağlanmalıdır.
     *
     * @param uri     istek atılacak olan adress
     * @param headers eklenmek istenen header içerikleri.
     * @return Flux dinlenmek üzere flux döner. request gerçek anlamda cevap dönmeden return çalışır. flux'ın dinlenmesi gerekir
     * @throws Exception
     */
    @Override
    public <D> Mono subscribe(URI uri,
                              HttpHeaders headers,
                              D json,
                              ParameterizedTypeReference<?> responseType) {
        return webClientBuilder(uri, headers, json)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, clientResponse ->
                        Mono.error(new RuntimeException())
                )
                .onStatus(HttpStatus::is5xxServerError, clientResponse ->
                        Mono.error(new RuntimeException())
                )
                .bodyToMono(responseType);
    }

    /**
     * Async request atmayı sağlar
     * istek gönderilir ve cevap gelmeden metod biter. İstek geldiğinde cevabı dinlemek için return değerine subscribe olmak gerekir.
     * örnek kullanımı demoUsage içerisinde mevcut
     * onStatus metodları ile istenmeyen status dönüşlerinde exception handle edilir ve istenilen sonuç dönülebilir
     * onStatus burada başlangıç olarak kullanılmıştır ve sadece RuntimeException fırlatılmıştır
     * dönen cevabın handle edilip uygun dönüşün sağlanması gerekir
     * NOT: gerekli loglama işlemleri yapılmamıştır Subscribe olunan metod ile birlikte logları sağlanmalıdır.
     *
     * @param uri     istek atılacak olan adress
     * @param headers eklenmek istenen header içerikleri.
     * @return Flux dinlenmek üzere flux döner. request gerçek anlamda cevap dönmeden return çalışır. flux'ın dinlenmesi gerekir
     * @throws Exception
     */
    @Override
    public <D> Mono<ClientResponse> subscribe(URI uri,
                                              HttpHeaders headers,
                                              D json) {
        return webClientBuilder(uri, headers, json)
                .exchange();
    }

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
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            HttpEntity<Object> entity;
            if(null !=json){
                entity = new HttpEntity<>(json, headers);
            }
            else{
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
