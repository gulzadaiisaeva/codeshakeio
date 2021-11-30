package com.example.codeshakeio.request;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

public interface CommonRequestResponseService {

    <T> ResponseEntity<T> getResponseByType(TypeReference<T> responseType, ResponseEntity<String> responseString);

    HttpHeaders prepareHttpHeader();

}
