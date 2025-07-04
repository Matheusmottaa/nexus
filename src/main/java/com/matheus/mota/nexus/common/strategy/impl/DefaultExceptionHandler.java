package com.matheus.mota.nexus.common.strategy.impl;

import com.matheus.mota.nexus.common.ProblemDetails;
import com.matheus.mota.nexus.common.strategy.ExceptionHandlerStrategy;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;

public class DefaultExceptionHandler implements ExceptionHandlerStrategy {

    @Override
    public boolean supports(Exception ex) {
        return true;
    }

    @Override
    public ProblemDetails handle(Exception ex, HttpServletRequest req) {
        return new ProblemDetails(
                "Unknown Error",
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                "An unexpected error has occurred.",
                req.getRequestURI()
        );
    }
}
