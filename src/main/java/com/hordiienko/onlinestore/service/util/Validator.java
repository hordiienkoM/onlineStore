package com.hordiienko.onlinestore.service.util;

import java.util.regex.Pattern;

public class Validator {

    public static boolean isEmail(String email) {
        final Pattern pattern = Pattern.compile("^\\S+@\\S+\\.\\S+$");
        return pattern.matcher(email).matches();
    }
}
