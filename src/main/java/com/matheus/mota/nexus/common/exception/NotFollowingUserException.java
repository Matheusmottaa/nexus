package com.matheus.mota.nexus.common.exception;

public class NotFollowingUserException extends RuntimeException {
    public NotFollowingUserException(String message) {
        super(message);
    }

    public NotFollowingUserException(String message, Throwable throwable) {
      super(message, throwable);
    }
}
