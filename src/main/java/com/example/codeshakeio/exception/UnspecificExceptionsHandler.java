package com.example.codeshakeio.exception;

import com.example.codeshakeio.common.CommonUtils;
import com.example.codeshakeio.exception.base.BaseCheckedException;
import com.example.codeshakeio.exception.base.BaseUncheckedException;
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

/**
 * Request işleme sırasında oluşan ve genel olarak aynı şekilde handle edilen exception lar bu controller advice e gelir
 */
@Order(value = Ordered.HIGHEST_PRECEDENCE + 1)
@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class UnspecificExceptionsHandler extends ResponseEntityExceptionHandler {


    //Bizim yazdığımız checked exception lar bu method da handle edilir
    @ExceptionHandler({BaseCheckedException.class})
    public ResponseEntity<String> handleBaseCheckedException(BaseCheckedException baseCheckedException, HttpServletRequest httpServletRequest) {

        String errorMessage = baseCheckedException.getMessage();
        log.error(errorMessage);

        return CommonUtils.createResponseEntityOnExceptionHandler(
                CommonUtils.extractHttpStatusFromException(baseCheckedException),
                HttpHeaders.EMPTY,
                baseCheckedException.getFailureResultCode(),
                errorMessage
        );
    }

    //Bizim yazdığımız unchecked exception lar bu method da handle edilir
    @ExceptionHandler({BaseUncheckedException.class})
    public ResponseEntity<String> handleBaseUncheckedException(BaseUncheckedException baseUncheckedException, HttpServletRequest httpServletRequest) {

        String errorMessage = baseUncheckedException.getMessage();
        log.error(errorMessage);

        return CommonUtils.createResponseEntityOnExceptionHandler(
                CommonUtils.extractHttpStatusFromException(baseUncheckedException),
                HttpHeaders.EMPTY,
                baseUncheckedException.getFailureResultCode(),
                errorMessage
        );
    }
}
