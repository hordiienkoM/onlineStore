package com.hordiienko.onlinestore.service.util;

import java.util.Random;

public class TokenUtil {

    public static String getToken() {
        int number = new Random().nextInt(999999);
        return String.format("%06d", number);
    }
}
