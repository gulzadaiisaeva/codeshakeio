package com.example.codeshakeio.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum OperationStatus {

    @JsonProperty("DONE") DONE,
    @JsonProperty("UNDONE") UNDONE
}
