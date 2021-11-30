package com.example.codeshakeio.exception.base;


import com.example.codeshakeio.enums.resultcode.FailureResultCode;

public class BaseCheckedException extends Exception implements CustomThrowable {

    private final FailureResultCode failureResultCode;

    public BaseCheckedException(RuntimeException e, FailureResultCode failureResultCode) {
        super(e);
        this.failureResultCode = failureResultCode;
    }

    public BaseCheckedException(String message,
                                FailureResultCode failureResultCode) {
        super(message);
        this.failureResultCode = failureResultCode;
    }

    public BaseCheckedException(String message,
                                Throwable cause,
                                FailureResultCode failureResultCode) {
        super(message, cause);
        this.failureResultCode = failureResultCode;
    }

    public FailureResultCode getFailureResultCode() {
        return failureResultCode;
    }

}
