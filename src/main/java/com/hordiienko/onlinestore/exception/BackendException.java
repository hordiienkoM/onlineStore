package com.hordiienko.onlinestore.exception;

import com.hordiienko.onlinestore.exception.locale_message.ExceptionMessages;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Locale;

@Getter
public class BackendException extends RuntimeException {
    private final HttpStatus status;
    private final String messageKey;
    private final Locale locale;

    public BackendException(String messageKey, HttpStatus status, Locale locale) {
        this.messageKey = messageKey;
        this.status = status;
        this.locale = locale;
    }

    public String getLocalizedMessage() {
        return ExceptionMessages.getMessageForLocale(messageKey, locale);
    }
}
