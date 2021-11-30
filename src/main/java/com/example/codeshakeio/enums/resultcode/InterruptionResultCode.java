package com.example.codeshakeio.enums.resultcode;

import com.fasterxml.jackson.annotation.JsonValue;

public enum InterruptionResultCode implements ResultCode {
    TEST_EXECUTION_INTERRUPTED(300000);

    public final Integer interruptionCode;

    InterruptionResultCode(Integer interruptionCode) {
        this.interruptionCode = interruptionCode;
    }

    @JsonValue
    @Override
    public Integer toInt() {
        return interruptionCode;
    }

    @Override
    public Integer getResultCode() {
        return interruptionCode;
    }
}
