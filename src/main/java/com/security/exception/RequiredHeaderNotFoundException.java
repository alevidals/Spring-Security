package com.security.exception;

public class RequiredHeaderNotFoundException extends RuntimeException {
    public RequiredHeaderNotFoundException(String message) {
        super(message);
    }
}
