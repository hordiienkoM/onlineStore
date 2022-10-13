package com.hordiienko.onlinestore.exception;

import org.springframework.http.HttpStatus;

public class EmailMessageException extends BackendException {
    public EmailMessageException() {
        super("Message not sent", HttpStatus.FAILED_DEPENDENCY);
    }
}
