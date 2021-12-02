package com.example.codeshakeio.common;

import com.example.codeshakeio.config.ObjectMapperConfiguration;
import com.example.codeshakeio.enums.resultcode.ResultCode;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

public class CommonUtils {

    /**
     * İstek karşılamada response oluştururken kod tekrarını azaltmak için yazılmış method
     *
     * @param httpStatus   Response un status code u
     * @param httpHeaders  response içinde gönderilemsi istenen extra headerlar
     * @param hitTime      isteğin geldiği tarih
     * @param responseTime isteğe cevap verildiği tarih
     * @param resultCode   isteğin result codu
     * @param t            response body objesi
     * @param <T>          response body tipi
     * @return Hazırlanan response entity
     */
    public static <T> ResponseEntity<T> createResponseEntity(HttpStatus httpStatus, HttpHeaders httpHeaders, LocalDateTime hitTime, LocalDateTime responseTime, ResultCode resultCode, T t) {
        return ResponseEntity.status(httpStatus)
                .headers(httpHeaders)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(CustomHeaderNames.REQUEST_HIT_TIME, hitTime.format(ObjectMapperConfiguration.DATE_TIME_FORMATTER))
                .header(CustomHeaderNames.RESULT_CODE, String.valueOf(resultCode.getResultCode()))
                .header(CustomHeaderNames.RESPONSE_TIME, responseTime.format(ObjectMapperConfiguration.DATE_TIME_FORMATTER))
                .body(t);
    }

    public static ResponseEntity<Void> createNoContentResponseEntity(LocalDateTime hitTime, LocalDateTime responseTime, ResultCode resultCode) {
        return ResponseEntity.noContent()
                .header(CustomHeaderNames.REQUEST_HIT_TIME, hitTime.format(ObjectMapperConfiguration.DATE_TIME_FORMATTER))
                .header(CustomHeaderNames.RESULT_CODE, String.valueOf(resultCode.getResultCode()))
                .header(CustomHeaderNames.RESPONSE_TIME, responseTime.format(ObjectMapperConfiguration.DATE_TIME_FORMATTER)).build();
    }
}
