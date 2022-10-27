package com.hordiienko.onlinestore.exception;

import org.springframework.http.HttpStatus;

import java.util.Locale;

public class UserNotFoundException extends BackendException {
    public UserNotFoundException(Locale locale) {
        super("error.user_not_found", HttpStatus.NOT_FOUND, locale);
    }
}
