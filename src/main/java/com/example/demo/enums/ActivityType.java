package com.example.demo.enums;

public enum ActivityType {
    NO_ACTIVITY("NO_ACTIVITY");
    private String str;

    private ActivityType(String str) {
        this.str = str;
    }

    public String getStr() {
        return str;
    }

}
