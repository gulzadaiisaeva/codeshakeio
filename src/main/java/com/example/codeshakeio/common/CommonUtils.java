package com.example.codeshakeio.common;

import com.example.codeshakeio.config.ObjectMapperConfiguration;
import com.example.codeshakeio.enums.resultcode.ResultCode;
import com.example.codeshakeio.exception.unchecked.ResourceNotFoundException;
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

    /**
     * Exception handler lar içinde response oluştururken kod tekrarını azaltmak için yazılmış method
     *
     * @param httpStatus  Response un status code u
     * @param httpHeaders response içinde gönderilemsi istenen extra headerlar
     * @param resultCode  isteğin result codu
     * @param t           response body objesi
     * @param <T>         response body tipi
     * @return Hazırlanan response entity
     */
    public static <T> ResponseEntity<T> createResponseEntityOnExceptionHandler(HttpStatus httpStatus, HttpHeaders httpHeaders, ResultCode resultCode, T t) {
        return ResponseEntity.status(httpStatus)
                .headers(httpHeaders)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(CustomHeaderNames.EXCEPTION_TIME, LocalDateTime.now().format(ObjectMapperConfiguration.DATE_TIME_FORMATTER))
                .header(CustomHeaderNames.RESULT_CODE, String.valueOf(resultCode.getResultCode()))
                .header(CustomHeaderNames.RESPONSE_TIME, LocalDateTime.now().format(ObjectMapperConfiguration.DATE_TIME_FORMATTER))
                .body(t);
    }

    /**
     * Exception handler lar içinde alınan exception a göre döndürülecek olan http status kodunu belirlemek için yazılan method
     *
     * @param throwable handle edilen exception
     * @return exception a göre döndürülecek olan status code
     */
    public static HttpStatus extractHttpStatusFromException(Throwable throwable) {

        if (throwable instanceof ResourceNotFoundException) {
            return HttpStatus.NOT_FOUND;

        }
        return HttpStatus.INTERNAL_SERVER_ERROR;

    }
}
