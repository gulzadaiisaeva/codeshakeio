package com.example.codeshakeio.request;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.net.URI;

public interface GetRequest {

    <T> ResponseEntity<T> get(URI uri, HttpHeaders headers, TypeReference<T> responseType) throws Exception;


}

