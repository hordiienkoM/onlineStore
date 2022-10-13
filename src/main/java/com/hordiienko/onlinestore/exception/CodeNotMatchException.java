package com.hordiienko.onlinestore.exception;

import org.springframework.http.HttpStatus;

public class CodeNotMatchException extends BackendException {
    public CodeNotMatchException() {
        super("Code not match", HttpStatus.UNAUTHORIZED);
    }
}
