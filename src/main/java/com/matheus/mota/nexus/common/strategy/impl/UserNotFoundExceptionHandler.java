package com.matheus.mota.nexus.common.strategy.impl;

import com.matheus.mota.nexus.common.ProblemDetails;
import com.matheus.mota.nexus.common.exception.UserNotFoundException;
import com.matheus.mota.nexus.common.strategy.ExceptionHandlerStrategy;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;

public class UserNotFoundExceptionHandler implements ExceptionHandlerStrategy {

    @Override
    public boolean supports(Exception ex) {
        return ex instanceof UserNotFoundException;
    }

    @Override
    public ProblemDetails handle(Exception ex, HttpServletRequest req) {
        String title = "User not found";
        String detail = "User not found for the provided ID";
        return new ProblemDetails(
                title,
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                detail,
                req.getRequestURI()
        );
    }
}
