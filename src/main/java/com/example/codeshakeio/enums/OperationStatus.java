package com.example.codeshakeio.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum OperationStatus {

    @JsonProperty("STUDENT") ADDED,
    @JsonProperty("TEACHER") DELETED
}
