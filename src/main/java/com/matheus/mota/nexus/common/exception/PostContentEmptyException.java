package com.matheus.mota.nexus.common.exception;

public class PostContentEmptyException extends RuntimeException {
    public PostContentEmptyException(String message) {
        super(message);
    }

    public PostContentEmptyException(String message, Throwable throwable) {
      super(message, throwable);
    }
}
