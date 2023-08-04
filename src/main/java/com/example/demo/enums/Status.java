package com.example.demo.enums;

public enum Status {
    INACTIVE("Inactive"),
    ACTIVE("Active");

    private String value;

    Status(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

