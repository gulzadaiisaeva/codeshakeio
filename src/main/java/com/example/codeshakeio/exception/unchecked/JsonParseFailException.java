package com.example.codeshakeio.exception.unchecked;

import com.example.codeshakeio.enums.resultcode.FailureResultCode;
import com.example.codeshakeio.exception.base.BaseUncheckedException;
import com.fasterxml.jackson.core.type.TypeReference;


public class JsonParseFailException extends BaseUncheckedException {

    private final String jsonString;
    private TypeReference<?> parseType;

    public JsonParseFailException(String message,
                                  FailureResultCode failureResultCode,
                                  String jsonString,
                                  TypeReference<?> parseType) {
        super(message, failureResultCode);

        this.jsonString = jsonString;
        this.parseType = parseType;
    }

    public JsonParseFailException(String message,
                                  Throwable cause,
                                  FailureResultCode failureResultCode,
                                  String jsonString,
                                  TypeReference<?> parseType) {
        super(message, cause, failureResultCode);

        this.jsonString = jsonString;
        this.parseType = parseType;
    }

    public JsonParseFailException(String message,
                                  Throwable cause,
                                  FailureResultCode failureResultCode,
                                  String jsonString) {
        super(message, cause, failureResultCode);

        this.jsonString = jsonString;
    }

    public String getJsonString() {
        return jsonString;
    }

    public TypeReference<?> getParseType() {
        return parseType;
    }
}