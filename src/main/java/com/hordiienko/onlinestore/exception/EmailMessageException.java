package com.hordiienko.onlinestore.exception;

import org.springframework.http.HttpStatus;

import java.util.Locale;

public class EmailMessageException extends BackendException {
    public EmailMessageException() {
        super("error.email_message", HttpStatus.FAILED_DEPENDENCY);
    }

    public EmailMessageException(Locale locale) {
        super("error.email_message", HttpStatus.FAILED_DEPENDENCY, locale);
    }
}
