package com.hordiienko.onlinestore.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends BackendException {
    public UserNotFoundException() {
        super("User not found", HttpStatus.NOT_FOUND);
    }
}
