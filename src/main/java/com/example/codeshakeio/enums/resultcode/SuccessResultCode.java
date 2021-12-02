package com.example.codeshakeio.enums.resultcode;

import com.fasterxml.jackson.annotation.JsonValue;

public enum SuccessResultCode implements ResultCode {

    /**
     * GENERAL
     */
    SUCCESS(200001),
    GET_INFO_SUCCESSFUL(200300), CREATE_SUCCESSFUL(200301),
    UPDATE_SUCCESSFUL(200302), DELETE_SUCCESSFUL(200303);


    public final Integer successCode;

    SuccessResultCode(Integer successCode) {
        this.successCode = successCode;
    }

    @JsonValue
    @Override
    public Integer toInt() {
        return successCode;
    }

    @Override
    public Integer getResultCode() {
        return successCode;
    }
}
