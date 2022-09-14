package com.hordiienko.onlinestore.exception;

public class OrderNotFoundException extends Exception {
    public OrderNotFoundException() {
        super("Order not found");
    }
}
