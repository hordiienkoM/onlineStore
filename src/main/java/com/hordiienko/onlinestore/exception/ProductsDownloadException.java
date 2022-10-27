package com.hordiienko.onlinestore.exception;

import org.springframework.http.HttpStatus;

import java.util.Locale;

public class ProductsDownloadException extends BackendException {
    public ProductsDownloadException(Locale locale) {
        super("error.products.download.error", HttpStatus.NOT_FOUND, locale);
    }
}
