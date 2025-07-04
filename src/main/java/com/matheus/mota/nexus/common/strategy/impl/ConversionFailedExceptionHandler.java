package com.matheus.mota.nexus.common.strategy.impl;

import com.matheus.mota.nexus.common.ProblemDetails;
import com.matheus.mota.nexus.common.strategy.ExceptionHandlerStrategy;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ConversionFailedExceptionHandler implements ExceptionHandlerStrategy {

    private static final String UNKNOWN_TYPE = "unknown type";

    private static final String VALUE_NOT_INFORMED = "value not informed";

    @Override
    public boolean supports(Exception ex) {
        return ex instanceof ConversionFailedException;
    }

    @Override
    public ProblemDetails handle(Exception exception, HttpServletRequest req) {
        ConversionFailedException ex = (ConversionFailedException) exception;
        String title = "Invalid field value provided";

        String invalidValue = Optional.ofNullable(ex.getValue())
                .map(Object::toString)
                .orElse(VALUE_NOT_INFORMED);
        String requiredType = Optional.of(ex.getTargetType())
                .map(typeDescriptor -> typeDescriptor.getType().getSimpleName())
                .orElse(UNKNOWN_TYPE);
        String detail = String.format("The value '%s' provided is invalid. A value of type '%s' was expected.", invalidValue, requiredType);

        return new ProblemDetails(title, HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), detail, req.getRequestURI());
    }
}
