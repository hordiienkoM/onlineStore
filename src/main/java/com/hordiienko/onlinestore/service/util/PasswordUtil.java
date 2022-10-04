package com.hordiienko.onlinestore.service.util;

public class PasswordUtil {

    public static String getToken() {
        String pass = "";
        for (int i = 0; i < 6; i++) {
            pass += (int)(Math.random()*9);
        }
        return new String(pass);
    }
}
