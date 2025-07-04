package com.matheus.mota.nexus.common.strategy.impl;

import com.matheus.mota.nexus.common.ProblemDetails;
import com.matheus.mota.nexus.common.exception.PostContentTooLongException;
import com.matheus.mota.nexus.common.strategy.ExceptionHandlerStrategy;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class PostContentTooLongExceptionHandler implements ExceptionHandlerStrategy {
    @Override
    public boolean supports(Exception ex) {
        return ex instanceof PostContentTooLongException;
    }

    @Override
    public ProblemDetails handle(Exception ex, HttpServletRequest req) {
        String title = "Post Content Too Long";
        String detail = "The post exceeds the maximum allowed length. Please shorten the content.";
        return new ProblemDetails(
                title,
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                detail,
                req.getRequestURI()
        );
    }
}
