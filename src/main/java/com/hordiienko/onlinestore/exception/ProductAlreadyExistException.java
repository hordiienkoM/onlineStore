package com.hordiienko.onlinestore.exception;

import org.springframework.http.HttpStatus;

import java.util.Locale;

public class ProductAlreadyExistException extends BackendException {
    public ProductAlreadyExistException(Locale locale) {
        super("error.product_already_exist", HttpStatus.CONFLICT, locale);
    }
}
