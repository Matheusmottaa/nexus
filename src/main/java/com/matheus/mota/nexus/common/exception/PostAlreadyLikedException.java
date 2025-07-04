package com.matheus.mota.nexus.common.exception;

public class PostAlreadyLikedException extends RuntimeException{

    public PostAlreadyLikedException(String message) {
        super(message);
    }

    public PostAlreadyLikedException(String message, Throwable throwable) {
      super(message, throwable);
    }
}
