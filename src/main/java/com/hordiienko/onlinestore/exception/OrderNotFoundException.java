package com.hordiienko.onlinestore.exception;

import org.springframework.http.HttpStatus;

public class OrderNotFoundException extends BackendException {
    public OrderNotFoundException() {
        super("Order not found", HttpStatus.NOT_FOUND);
    }
}
