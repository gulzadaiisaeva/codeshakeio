package com.example.codeshakeio.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum OperationType {

    @JsonProperty("ADD") ADD,
    @JsonProperty("DELETE") DELETE
}
