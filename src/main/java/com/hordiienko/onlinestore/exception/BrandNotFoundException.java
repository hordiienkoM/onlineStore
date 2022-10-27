package com.hordiienko.onlinestore.exception;

import org.springframework.http.HttpStatus;

import java.util.Locale;

public class BrandNotFoundException extends BackendException {
    public BrandNotFoundException(Locale locale) {
        super("error.brand_not_found", HttpStatus.NOT_FOUND, locale);
    }
}
