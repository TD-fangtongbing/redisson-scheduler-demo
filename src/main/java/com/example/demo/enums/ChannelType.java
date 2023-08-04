package com.example.demo.enums;

public enum ChannelType {
    SMS("sms"),
    CHAT("chat"),
    EMAIL("email"),
    FACEBOOK_MESSENGER("facebook_messenger"),
    WHATSAPP("whatsapp"),
    DIGITAL_CONNECT("digital_connect");

    private final String value;

    ChannelType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

