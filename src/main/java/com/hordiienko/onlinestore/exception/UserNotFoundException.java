package com.hordiienko.onlinestore.exception;

public class UserNotFoundException extends Exception {
    public UserNotFoundException() {
        super("User not found");
    }
}
