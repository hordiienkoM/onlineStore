package com.hordiienko.onlinestore.exception;

import org.springframework.http.HttpStatus;

import java.util.Locale;

public class CodeNotMatchException extends BackendException {
    public CodeNotMatchException() {
        super("error.code_not_match", HttpStatus.UNAUTHORIZED);
    }

    public CodeNotMatchException(Locale locale) {
        super("error.code_not_match", HttpStatus.UNAUTHORIZED, locale);
    }
}
