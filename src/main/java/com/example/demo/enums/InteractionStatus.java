package com.example.demo.enums;

public enum InteractionStatus {
    ASSIGNED("ASSIGNED"),
    UNASSIGNED("UNASSIGNED");
    private String str;

    private InteractionStatus(String str) {
        this.str = str;
    }

    public String getStr() {
        return str;
    }
}
