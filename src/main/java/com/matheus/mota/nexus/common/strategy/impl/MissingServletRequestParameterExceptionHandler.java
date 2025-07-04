package com.matheus.mota.nexus.common.strategy.impl;

import com.matheus.mota.nexus.common.ProblemDetails;
import com.matheus.mota.nexus.common.strategy.ExceptionHandlerStrategy;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MissingServletRequestParameterException;

@Component
public class MissingServletRequestParameterExceptionHandler implements ExceptionHandlerStrategy {

    @Override
    public boolean supports(Exception ex) {
        return ex instanceof MissingServletRequestParameterException;
    }

    @Override
    public ProblemDetails handle(Exception exception, HttpServletRequest req) {
        MissingServletRequestParameterException ex = (MissingServletRequestParameterException) exception;
        String title = "Field not provided";
        String detail = String.format("The field '%s' was not provided.", ex.getParameterName());
        return new ProblemDetails(
                title,
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                detail,
                req.getRequestURI()
        );
    }
}
