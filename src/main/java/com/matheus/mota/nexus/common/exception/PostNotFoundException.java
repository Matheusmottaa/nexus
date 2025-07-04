package com.matheus.mota.nexus.common.exception;

public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException(String message) {
        super(message);
    }

    public PostNotFoundException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
