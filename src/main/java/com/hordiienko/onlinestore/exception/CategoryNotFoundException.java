package com.hordiienko.onlinestore.exception;

import org.springframework.http.HttpStatus;

import java.util.Locale;

public class CategoryNotFoundException extends BackendException {
    public CategoryNotFoundException(Locale locale) {
        super("error.category_not_found", HttpStatus.NOT_FOUND, locale);
    }
}
