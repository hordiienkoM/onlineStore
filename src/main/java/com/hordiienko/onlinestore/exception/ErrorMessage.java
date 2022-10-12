package com.hordiienko.onlinestore.exception;

import lombok.*;


@AllArgsConstructor
@Getter
@Setter
public class ErrorMessage {
    private String exceptionName;
    private String message;

    ErrorMessage(String message){
        this.message = message;
    }
}
