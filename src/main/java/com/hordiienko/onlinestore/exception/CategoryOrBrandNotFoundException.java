package com.hordiienko.onlinestore.exception;

import org.springframework.http.HttpStatus;

import java.util.Locale;

public class CategoryOrBrandNotFoundException extends BackendException {
    public CategoryOrBrandNotFoundException(Locale locale) {
        super("error.category-or-brand_not_found", HttpStatus.NOT_FOUND, locale);
    }
}
