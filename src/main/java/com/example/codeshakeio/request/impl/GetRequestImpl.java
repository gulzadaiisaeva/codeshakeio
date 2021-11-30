package com.example.codeshakeio.request.impl;

import com.example.codeshakeio.request.CommonRequestResponseService;
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
public class GetRequestImpl implements GetRequest {
    //WebClient Non-Blocking Client
    //WebClient detaylarına aşağıdaki adresten bak
    //https://docs.spring.io/spring/docs/5.0.16.RELEASE/spring-framework-reference/web-reactive.html

    private final CommonRequestResponseService commonRequestResponseService;
    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * oluşturulmuş olan WebClient Methodlarını test edebilmek ve genel kullanımını sağlamak için oluşturuldu
     *
     * @throws Exception
     */

    /**
     * Diğer metodlara builder olarak çalışır
     *
     * @param uri     istek atılacak olan adress
     * @param headers eklenmek istenen header içerikleri.
     * @return
     * @throws Exception
     */
    @Override
    public WebClient.RequestHeadersSpec<?> webClientBuilder(URI uri,
                                                            HttpHeaders headers) {

        /**
         *  .uri(uriBuilder -> uriBuilder.path("/user/repos")
         *                             .queryParam("sort", "updated")
         *                             .queryParam("direction", "desc")
         *                             .build())
         */
        return WebClient.create()
                .get()
                .uri(String.valueOf(uri))
                .headers(httpHeaders -> httpHeaders.addAll(Optional.ofNullable(headers).orElse(new HttpHeaders())));
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
     * @param clazz   body değeri hangi class objesinde gelecek ise onun class değeri
     * @return Flux dinlenmek üzere flux döner. request gerçek anlamda cevap dönmeden return çalışır. flux'ın dinlenmesi gerekir
     * @throws Exception
     */
    @Override
    public Flux subscribe(URI uri,
                          HttpHeaders headers,
                          Class clazz) {
        return webClientBuilder(uri, headers)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, clientResponse ->
                        Mono.error(new RuntimeException())
                )
                .onStatus(HttpStatus::is5xxServerError, clientResponse ->
                        Mono.error(new RuntimeException())
                )
                .bodyToFlux(clazz);
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
