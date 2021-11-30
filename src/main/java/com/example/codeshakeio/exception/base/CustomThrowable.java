package com.example.codeshakeio.exception.base;


import com.example.codeshakeio.enums.resultcode.FailureResultCode;

public interface CustomThrowable {
    FailureResultCode getFailureResultCode();
}