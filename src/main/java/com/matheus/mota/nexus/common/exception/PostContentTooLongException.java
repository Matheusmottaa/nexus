package com.matheus.mota.nexus.common.exception;

public class PostContentTooLongException extends RuntimeException {
    public PostContentTooLongException(String message) {
        super(message);
    }

    public PostContentTooLongException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
