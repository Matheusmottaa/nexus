package com.matheus.mota.nexus.common.exception;

public class AlreadyFollowingUserException extends RuntimeException {
  public AlreadyFollowingUserException(String message) {
    super(message);
  }

  public AlreadyFollowingUserException(String message, Throwable throwable) {
      super(message, throwable);
  }
}
