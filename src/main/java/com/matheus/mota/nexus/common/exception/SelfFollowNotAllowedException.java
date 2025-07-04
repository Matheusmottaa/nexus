package com.matheus.mota.nexus.common.exception;

public class SelfFollowNotAllowedException extends RuntimeException {
    public SelfFollowNotAllowedException(String message) {
        super(message);
    }

    public SelfFollowNotAllowedException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
