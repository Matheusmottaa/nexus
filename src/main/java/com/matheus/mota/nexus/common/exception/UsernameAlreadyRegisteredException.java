package com.matheus.mota.nexus.common.exception;

public class UsernameAlreadyRegisteredException extends RuntimeException{

    public UsernameAlreadyRegisteredException(String message) {
        super(message);
    }

    public UsernameAlreadyRegisteredException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
