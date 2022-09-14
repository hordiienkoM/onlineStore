package com.hordiienko.onlinestore.exception;

public class UserAlreadyExistException extends Exception {
    public UserAlreadyExistException() {
        super("A user with this name already exist");
    }
}
