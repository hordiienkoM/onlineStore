package com.hordiienko.onlinestore.exception;

import org.springframework.http.HttpStatus;

import java.util.Locale;

public class UserAlreadyExistException extends BackendException {
    public UserAlreadyExistException(Locale locale) {
        super("error.user_already_exist", HttpStatus.CONFLICT, locale);
    }
}
