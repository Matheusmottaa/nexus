package com.matheus.mota.nexus.common.strategy;

import com.matheus.mota.nexus.common.ProblemDetails;
import com.matheus.mota.nexus.common.strategy.impl.DefaultExceptionHandler;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ExceptionHandlerComposite {

    private final List<ExceptionHandlerStrategy> strategies;

    public ExceptionHandlerComposite(List<ExceptionHandlerStrategy> strategies) {
        this.strategies = strategies;
    }

    public ProblemDetails resolve(Exception ex, HttpServletRequest req) {
        return strategies.stream()
                .filter(strategy -> strategy.supports(ex))
                .findFirst()
                .orElse(new DefaultExceptionHandler())
                .handle(ex, req);
    }
}
