package com.matheus.mota.nexus.common.strategy.impl;

import com.matheus.mota.nexus.common.ProblemDetails;
import com.matheus.mota.nexus.common.exception.PostAlreadyNotLikedException;
import com.matheus.mota.nexus.common.strategy.ExceptionHandlerStrategy;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class PostAlreadyNotLikedExceptionHandler implements ExceptionHandlerStrategy {
    @Override
    public boolean supports(Exception ex) {
        return ex instanceof PostAlreadyNotLikedException;
    }

    @Override
    public ProblemDetails handle(Exception ex, HttpServletRequest req) {
        String title = "Post Already Disliked";
        String detail = "You have already marked this post as disliked. You cannot dislike it again.";
        return new ProblemDetails(
                title,
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.getReasonPhrase(),
                detail,
                req.getRequestURI()
        );
    }
}
