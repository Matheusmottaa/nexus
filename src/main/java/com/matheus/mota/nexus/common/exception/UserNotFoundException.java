package com.matheus.mota.nexus.common.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
