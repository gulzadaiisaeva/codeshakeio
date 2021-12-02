package com.example.codeshakeio.exception.unchecked;

import com.example.codeshakeio.enums.resultcode.FailureResultCode;
import com.example.codeshakeio.exception.base.BaseUncheckedException;
import lombok.Builder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends BaseUncheckedException {

    @Builder
    public ResourceNotFoundException(String message,
                                     Throwable cause,
                                     FailureResultCode failureResultCode) {
        super(message, cause, failureResultCode);
    }

    @Builder
    public ResourceNotFoundException(String message,
                                     FailureResultCode failureResultCode) {
        super(message, failureResultCode);
    }
}