package com.hordiienko.onlinestore.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ApiException extends Exception{
    private List<String> exceptions;
}
