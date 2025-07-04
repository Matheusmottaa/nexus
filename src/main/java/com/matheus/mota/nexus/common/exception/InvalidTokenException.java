package com.matheus.mota.nexus.common.exception;

public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException(String message) {
        super(message);
    }

    public InvalidTokenException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
