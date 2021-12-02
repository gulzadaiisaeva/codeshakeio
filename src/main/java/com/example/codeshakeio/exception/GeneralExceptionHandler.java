package com.example.codeshakeio.exception;

import com.example.codeshakeio.common.CommonUtils;
import com.example.codeshakeio.enums.resultcode.FailureResultCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@Order(value = Ordered.LOWEST_PRECEDENCE)
@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class GeneralExceptionHandler extends ResponseEntityExceptionHandler {


    //Uygulama içinde ve controller advice lar içinde yakalanmayan exception lar buraya gelir
    @ExceptionHandler({Exception.class})
    public ResponseEntity<String> handleGeneralException(Exception unCaughtException, HttpServletRequest httpServletRequest) {

        String errorMessage = unCaughtException.getMessage();
        log.error(errorMessage);

        return CommonUtils.createResponseEntityOnExceptionHandler(
                CommonUtils.extractHttpStatusFromException(unCaughtException),
                HttpHeaders.EMPTY,
                FailureResultCode.UNRECOGNIZED_ERROR,
                unCaughtException.getMessage()
        );
    }
}
