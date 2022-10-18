package com.hordiienko.onlinestore.exception;

import org.springframework.http.HttpStatus;

import java.util.Locale;

public class OrderNotFoundException extends BackendException {
    public OrderNotFoundException() {
        super("error.order_not_found", HttpStatus.NOT_FOUND);
    }

    public OrderNotFoundException(Locale locale) {
        super("error.order_not_found", HttpStatus.NOT_FOUND, locale);
    }
}
