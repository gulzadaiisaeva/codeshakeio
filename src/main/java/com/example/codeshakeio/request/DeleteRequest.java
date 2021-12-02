package com.example.codeshakeio.request;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;

import java.net.URI;


public interface DeleteRequest {

    <T> Object delete(URI uri, HttpHeaders headers, Object json, ParameterizedTypeReference responseType) throws Exception;
}
