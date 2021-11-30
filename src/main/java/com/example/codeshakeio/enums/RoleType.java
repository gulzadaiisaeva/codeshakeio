package com.example.codeshakeio.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum RoleType {

    @JsonProperty("STUDENT") STUDENT,
    @JsonProperty("TEACHER") TEACHER,
    @JsonProperty("PARENT") PARENT
}
