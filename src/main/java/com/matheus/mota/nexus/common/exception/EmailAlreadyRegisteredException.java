package com.matheus.mota.nexus.common.exception;

public class EmailAlreadyRegisteredException extends RuntimeException{

    public EmailAlreadyRegisteredException(String message) {
        super(message);
    }

    public EmailAlreadyRegisteredException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
