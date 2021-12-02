package com.example.codeshakeio.request;

import com.example.codeshakeio.enums.resultcode.ResultCode;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

public interface CommonRequestResponseService {

    <T> ResponseEntity<T> getResponseByType(TypeReference<T> responseType, ResponseEntity<String> responseString);

    HttpHeaders prepareHttpHeader();

    <T> ResponseEntity<T> createResponseEntity(HttpStatus httpStatus, HttpHeaders httpHeaders,
                                               LocalDateTime hitTime, LocalDateTime responseTime, ResultCode resultCode, T t);

}
