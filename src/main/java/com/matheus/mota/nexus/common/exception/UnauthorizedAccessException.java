package com.matheus.mota.nexus.common.exception;

public class UnauthorizedAccessException extends RuntimeException {
    public UnauthorizedAccessException(String message) {
        super(message);
    }

    public UnauthorizedAccessException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
