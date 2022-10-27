package com.hordiienko.onlinestore.exception;

import org.springframework.http.HttpStatus;

import java.util.Locale;

public class ProductNotFoundException extends BackendException {
    public ProductNotFoundException(Locale locale) {
        super("error.product_not_found", HttpStatus.NOT_FOUND, locale);
    }
}
