package com.matheus.mota.nexus.common.exception;

public class UnauthorizedPostDeleteException extends RuntimeException {
    public UnauthorizedPostDeleteException(String message) {
        super(message);
    }

    public UnauthorizedPostDeleteException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
