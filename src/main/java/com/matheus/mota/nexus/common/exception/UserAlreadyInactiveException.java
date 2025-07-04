package com.matheus.mota.nexus.common.exception;

public class UserAlreadyInactiveException extends RuntimeException {
    public UserAlreadyInactiveException(String message) {
        super(message);
    }

    public UserAlreadyInactiveException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
