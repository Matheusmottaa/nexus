package com.matheus.mota.nexus.common.strategy.impl;

import com.matheus.mota.nexus.common.ProblemDetails;
import com.matheus.mota.nexus.common.strategy.ExceptionHandlerStrategy;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Optional;

@Component
public class MethodArgumentNotValidExceptionHandler implements ExceptionHandlerStrategy {
    @Override
    public boolean supports(Exception ex) {
        return ex instanceof MethodArgumentNotValidException;
    }

    @Override
    public ProblemDetails handle(Exception exception, HttpServletRequest req) {

        MethodArgumentNotValidException ex = (MethodArgumentNotValidException) exception;

        String title = "Field validation violated";
        String failedValidationMessage = "Validation failed on an unidentified field.";
        String detail;

        if (ex.getBindingResult().hasFieldErrors()) {
            FieldError fieldError = ex.getBindingResult().getFieldError();
            if (fieldError != null) {
                String violatedField = fieldError.getField();
                String errorMessage = fieldError.getDefaultMessage();

                if (errorMessage != null && errorMessage.contains("Failed to convert value of type")) {
                    String rejectedValue = Optional.ofNullable(fieldError.getRejectedValue())
                            .map(Object::toString)
                            .orElse("UNSPECIFIED_VALUE");
                    errorMessage = String.format("'%s' is not valid.", rejectedValue);
                }

                detail = String.format("Field validation for '%s' failed: %s.", violatedField, errorMessage);
            } else {
                detail = failedValidationMessage;
            }
        } else {
            detail = failedValidationMessage;
        }

        return new ProblemDetails(
                title,
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                detail,
                req.getRequestURI()
        );
    }
}
