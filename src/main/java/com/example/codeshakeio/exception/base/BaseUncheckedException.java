package com.example.codeshakeio.exception.base;

import com.example.codeshakeio.enums.resultcode.FailureResultCode;
import org.springframework.validation.BindingResult;

public class BaseUncheckedException extends RuntimeException implements CustomThrowable {

    private final FailureResultCode failureResultCode;

    public BaseUncheckedException(RuntimeException e, FailureResultCode failureResultCode) {
        super(e);
        this.failureResultCode = failureResultCode;
    }

    public BaseUncheckedException(String message, Throwable cause, FailureResultCode failureResultCode) {
        super(message, cause);
        this.failureResultCode = failureResultCode;
    }

    public BaseUncheckedException(String message, FailureResultCode failureResultCode) {
        super(message);
        this.failureResultCode = failureResultCode;
    }

    public BaseUncheckedException(BindingResult bindingResult, FailureResultCode failureResultCode) {
        super(String.valueOf(bindingResult.getAllErrors()));
        this.failureResultCode = failureResultCode;
    }


    public FailureResultCode getFailureResultCode() {
        return failureResultCode;
    }
}