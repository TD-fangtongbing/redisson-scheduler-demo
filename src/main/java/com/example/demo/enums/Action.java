package com.example.demo.enums;

public enum Action {
    SEND_TO_INBOX("SEND_TO_INBOX");
    private String str;

    private Action(String str) {
        this.str = str;
    }

    public String getStr() {
        return str;
    }

}
