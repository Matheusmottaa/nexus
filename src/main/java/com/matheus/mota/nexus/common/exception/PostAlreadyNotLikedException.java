package com.matheus.mota.nexus.common.exception;

public class PostAlreadyNotLikedException extends RuntimeException {
    public PostAlreadyNotLikedException(String message) {
        super(message);
    }

    public PostAlreadyNotLikedException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
