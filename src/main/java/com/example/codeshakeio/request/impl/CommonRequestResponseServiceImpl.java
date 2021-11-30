package com.example.codeshakeio.request.impl;

import com.example.codeshakeio.request.CommonRequestResponseService;
import com.example.codeshakeio.utils.JsonUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommonRequestResponseServiceImpl implements CommonRequestResponseService {


    @Override
    public HttpHeaders prepareHttpHeader() {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer eyJraWQiOiIwNDkzNjg4MTQ3ZDEyZTllZTZiZTdhOTlmYTM5YzhjZDg1NjhkNjRkIiwiYWxnIjoiUlMyNTYifQ.eyJucyI6IjQ5OTBkNmUwLWFjMDYtNDA5Zi05NWY2LTQ1OTUzNDBjZDk5MiIsIm5hbWUiOiJHdWx6YWRhIElpc2FldmEiLCJlbWFpbCI6Imlpc2FldmFndWx6YWRhQGdtYWlsLmNvbSIsImlzcyI6Imp3dC1zaWduZXJAY29kZXNoYWtlLWdhdGVrZWVwZXIuaWFtLmdzZXJ2aWNlYWNjb3VudC5jb20iLCJpYXQiOjE2Mzc5MzA0NjYsImF1ZCI6ImlvLmNvZGVzaGFrZS5nYXRla2VlcGVyIiwiZXhwIjoxNjM4Nzk0NDY2fQ.lvwcgLdR4FT5iYqjxdryGGh-gPR_3tJT9dzWpxETWp_hvRnDTC8cRlApYHhIJ3uS865gOew035VdEJmXV589PDBy6OmYzWYokz7pyGPlimn7DQ8KvuLW-lkpVtmXBuI-gK6dHqD8jW438RysF0KZ_bjhtra-6fgjP2eZ_vGv9xm3jbYkN2OgDymkYHNDjQhTCn35cHBV3utd5mqzU4TzSrHc3xwqA9gg6f1UyQeNGLJ8_Bu0foAy6tVB3zrb-K176diD0YJqkBqt8mqw4Jwk3g0677jhb3u6fVgu3-ON2t0-T8dXBRcOTblAh1FHVMLpERFWS0A009hUj8_TzBgZTw");
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return httpHeaders;


    }

    @Override
    public <T> ResponseEntity<T> getResponseByType(TypeReference<T> responseType, ResponseEntity<String> responseString) {
        //Gelen requesti birebir aynı kaydedebilmek için yapıldı
        ResponseEntity.BodyBuilder bodyBuilder = ResponseEntity
                .status(responseString.getStatusCode())
                .headers(responseString.getHeaders());

        ResponseEntity<T> response = null;
        if (responseType.getType().equals(String.class)) {
            response = (ResponseEntity<T>) bodyBuilder
                    .body(responseString.getBody());
        } else {
            response = (ResponseEntity<T>) bodyBuilder
                    .body(JsonUtils.jsonToClass(responseString.getBody(), responseType));
        }

        return response;
    }
}
