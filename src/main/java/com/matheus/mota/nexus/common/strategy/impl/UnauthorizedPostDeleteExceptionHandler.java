package com.matheus.mota.nexus.common.strategy.impl;

import com.matheus.mota.nexus.common.ProblemDetails;
import com.matheus.mota.nexus.common.exception.UnauthorizedPostDeleteException;
import com.matheus.mota.nexus.common.strategy.ExceptionHandlerStrategy;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class UnauthorizedPostDeleteExceptionHandler implements ExceptionHandlerStrategy {

    @Override
    public boolean supports(Exception ex) {
        return ex instanceof UnauthorizedPostDeleteException;
    }

    @Override
    public ProblemDetails handle(Exception ex, HttpServletRequest req) {
        String title = "Unauthorized Post Deletion";
        String detail = "You do not have permission to delete this post. Only the author can delete their posts.";

        return new ProblemDetails(
                title,
                HttpStatus.FORBIDDEN.value(),
                HttpStatus.FORBIDDEN.getReasonPhrase(),
                detail,
                req.getRequestURI()
        );
    }
}
