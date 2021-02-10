package com.vignesh.transactionaldemo.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Gender {
    M("m"), F("f"), Others("o");

    private final String value;

    Gender(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
