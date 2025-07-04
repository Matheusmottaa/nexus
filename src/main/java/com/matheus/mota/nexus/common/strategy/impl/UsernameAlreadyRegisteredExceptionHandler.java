package com.matheus.mota.nexus.common.strategy.impl;

import com.matheus.mota.nexus.common.ProblemDetails;
import com.matheus.mota.nexus.common.exception.UsernameAlreadyRegisteredException;
import com.matheus.mota.nexus.common.strategy.ExceptionHandlerStrategy;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class UsernameAlreadyRegisteredExceptionHandler implements ExceptionHandlerStrategy {

    @Override
    public boolean supports(Exception ex) {
        return ex instanceof UsernameAlreadyRegisteredException;
    }

    @Override
    public ProblemDetails handle(Exception ex, HttpServletRequest req) {
        String title = "Username already taken";
        String detail = "The provided username is already in use";

        return new ProblemDetails(
                title,
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.getReasonPhrase(),
                detail,
                req.getRequestURI()
        );
    }
}
