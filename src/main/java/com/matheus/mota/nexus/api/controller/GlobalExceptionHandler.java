package com.matheus.mota.nexus.api.controller;

import com.matheus.mota.nexus.common.ProblemDetails;
import com.matheus.mota.nexus.common.exception.*;
import com.matheus.mota.nexus.common.strategy.ExceptionHandlerComposite;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private static final String BAD_AUTHENTICATION = "Invalid authentication credentials";

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private final ExceptionHandlerComposite composite;

    @ExceptionHandler({
            MethodArgumentTypeMismatchException.class,
            MissingServletRequestParameterException.class,
            DataIntegrityViolationException.class,
            MethodArgumentNotValidException.class,
            ConversionFailedException.class,
            UserAlreadyActiveException.class,
            UserAlreadyInactiveException.class,
            EmailAlreadyRegisteredException.class,
            UsernameAlreadyRegisteredException.class,
            PostAlreadyLikedException.class,
            PostAlreadyNotLikedException.class,
            PostNotFoundException.class,
            SelfFollowNotAllowedException.class,
            SelfUnFollowNotAllowedException.class,
            AlreadyFollowingUserException.class,
            NotFollowingUserException.class
    })
    public ResponseEntity<ProblemDetails> handleBusinessException(Exception ex, HttpServletRequest req) {
        ProblemDetails detail = composite.resolve(ex, req);
        log.warn("Handled business exception: {} - URI: {} - Message: {}", ex.getClass().getSimpleName(), req.getRequestURI(), ex.getMessage());
        return ResponseEntity.status(detail.code()).body(detail);
    }

    @ExceptionHandler({ InvalidTokenException.class, UnauthorizedAccessException.class })
    public ResponseEntity<ProblemDetails> handleCustomAuthExceptions(RuntimeException ex, HttpServletRequest req) {
        ProblemDetails detail = new ProblemDetails(
                "Authentication Failure",
                HttpStatus.UNAUTHORIZED.value(),
                HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                BAD_AUTHENTICATION,
                req.getRequestURI()
        );
        log.warn("Authentication failure: {} - URI: {}", ex.getMessage(), req.getRequestURI(), ex);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(detail);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ProblemDetails> handleUnexpectedError(Throwable ex, HttpServletRequest req) {
        log.error("Unexpected error: {} - URI: {}", ex.getClass().getName(), req.getRequestURI(), ex);

        ProblemDetails detail = new ProblemDetails(
                "Unexpected Error",
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                "An unexpected internal error has occurred.",
                req.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(detail);
    }
}
