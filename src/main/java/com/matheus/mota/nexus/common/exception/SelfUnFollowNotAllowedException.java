package com.matheus.mota.nexus.common.exception;

public class SelfUnFollowNotAllowedException extends RuntimeException {
    public SelfUnFollowNotAllowedException(String message) {
        super(message);
    }

    public SelfUnFollowNotAllowedException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
