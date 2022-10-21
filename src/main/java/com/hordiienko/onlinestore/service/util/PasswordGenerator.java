package com.hordiienko.onlinestore.service.util;

import org.apache.commons.text.RandomStringGenerator;
import org.springframework.security.crypto.bcrypt.BCrypt;

public class PasswordGenerator {
    public static String getPassword(){
        RandomStringGenerator alphabeticChars = new RandomStringGenerator.Builder().withinRange(97, 122)
                .build();
        RandomStringGenerator numChars = new RandomStringGenerator.Builder().withinRange(48, 57)
                .build();
        return alphabeticChars.generate(4) + numChars.generate(2);
    }
}
