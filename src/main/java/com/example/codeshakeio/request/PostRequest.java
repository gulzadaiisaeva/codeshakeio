package com.example.codeshakeio.request;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostRequest {

    @NonNull
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;


    public <T> Object postJson(Object json, String uri, HttpHeaders headers, ParameterizedTypeReference responseType)
            throws Exception {

        ResponseEntity<? extends Object> response = null;
        try {
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            HttpEntity<Object> entity = new HttpEntity<>(json, headers);

            response = restTemplate.exchange(uri, HttpMethod.POST, entity, responseType);

        } catch (HttpStatusCodeException e) {
            response = ResponseEntity.status(e.getRawStatusCode()).headers(e.getResponseHeaders())
                    .body(e.getResponseBodyAsString());
        } catch (Exception e) {
            throw new Exception(e);
        }

        return response;
    }


}
