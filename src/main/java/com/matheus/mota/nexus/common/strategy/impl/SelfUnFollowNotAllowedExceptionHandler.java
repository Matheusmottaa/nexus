package com.matheus.mota.nexus.common.strategy.impl;

import com.matheus.mota.nexus.common.ProblemDetails;
import com.matheus.mota.nexus.common.exception.SelfUnFollowNotAllowedException;
import com.matheus.mota.nexus.common.strategy.ExceptionHandlerStrategy;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class SelfUnFollowNotAllowedExceptionHandler implements ExceptionHandlerStrategy {

    @Override
    public boolean supports(Exception ex) {
        return ex instanceof SelfUnFollowNotAllowedException;
    }

    @Override
    public ProblemDetails handle(Exception ex, HttpServletRequest req) {
        String title = "Invalid Unfollow Action";
        String detail = "You can't unfollow yourself.";
        return new ProblemDetails(
                title,
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                detail,
                req.getRequestURI()
        );
    }
}
