package com.hordiienko.onlinestore.exception;

import org.springframework.http.HttpStatus;

public class UserAlreadyExistException extends BackendException {
    public UserAlreadyExistException() {
        super("A user with this name already exist", HttpStatus.CONFLICT);
    }
}
