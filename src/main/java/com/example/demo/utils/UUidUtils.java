package com.example.demo.utils;

import java.util.UUID;

public class UUidUtils {

    public static String getUUid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

}
