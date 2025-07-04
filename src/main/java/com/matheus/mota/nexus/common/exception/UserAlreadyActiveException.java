package com.matheus.mota.nexus.common.exception;

public class UserAlreadyActiveException extends RuntimeException {
    public UserAlreadyActiveException(String message) {
        super(message);
    }

    public UserAlreadyActiveException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
