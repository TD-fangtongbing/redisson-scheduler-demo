package com.example.demo.enums;

public enum TimeoutUnit {
    MINUTES("MINUTES"),
    HOURS("HOURS"),
    DAYS("DAYS");
    private String str;

    private TimeoutUnit(String str) {
        this.str = str;
    }

    public String getStr() {
        return str;
    }

}
