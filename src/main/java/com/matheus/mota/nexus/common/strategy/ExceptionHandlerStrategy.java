package com.matheus.mota.nexus.common.strategy;

import com.matheus.mota.nexus.common.ProblemDetails;
import jakarta.servlet.http.HttpServletRequest;

public interface ExceptionHandlerStrategy {
    boolean supports(Exception ex);
    ProblemDetails handle(Exception ex, HttpServletRequest req);
}
