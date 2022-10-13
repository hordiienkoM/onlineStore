package com.hordiienko.onlinestore.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BackendException extends RuntimeException {
    private final HttpStatus status;

    public BackendException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
