package com.matheus.mota.nexus.common.util;

import com.matheus.mota.nexus.common.ProblemDetails;
import com.matheus.mota.nexus.common.exception.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;

import java.util.Optional;

public final class ExceptionUtil {

    private static final String UNKNOWN_TYPE = "unknown type";

    private static final String VALUE_NOT_INFORMED = "value not informed";

    public ExceptionUtil() {
        throw new IllegalStateException("Cannot be instantiated!");
    }

    public static ProblemDetails getProblemDetails(HttpServletRequest req, Exception ex) {
        if (ex instanceof UserNotFoundException) {
            return handleUserNotFoundException(req);
        }

        else if (ex instanceof EmailAlreadyRegisteredException e) {
            return handleEmailAlreadyRegistered(e, req);
        }

        else if (ex instanceof UsernameAlreadyRegisteredException e) {
            return handleUsernameAlreadyRegistered(e, req);
        }

        else if (ex instanceof UserAlreadyActiveException) {
            return handleUserAlreadyActive(req);
        }

        else if (ex instanceof UserAlreadyInactiveException) {
            return handleUserAlreadyInactive(req);
        }

        else if (ex instanceof DataIntegrityViolationException) {
            return handleDataIntegrityViolation(req);
        }

        else if (ex instanceof MissingServletRequestParameterException e) {
            return handleMissingServletRequestParameter(e, req);
        }

        else if (ex instanceof MethodArgumentNotValidException e) {
            return handleMethodArgumentNotValid(e, req);
        }

        else if (ex instanceof ConversionFailedException e) {
            return handleConversionFailed(e, req);
        }

        else if(ex instanceof SelfFollowNotAllowedException) {
            return handleSelfFollow(req);
        }

        else if(ex instanceof SelfUnFollowNotAllowedException) {
            return handleSelfUnfollow(req);
        }

        else if(ex instanceof PostAlreadyLikedException) {
            return handlePostAlreadyLike(req);
        }

        else if(ex instanceof PostAlreadyNotLikedException) {
            return handlePostAlreadyNotLiked(req);
        }

        else if(ex instanceof PostNotFoundException) {
            return handlePostNotFound(req);
        }

        else if(ex instanceof AlreadyFollowingUserException) {
            return handleAlreadyFollowingUser(req);
        }

        else if(ex instanceof NotFollowingUserException) {
            return handleAlreadyNotFollowingUser(req);
        }

        else if(ex instanceof PostContentEmptyException) {
            return handleContentPostEmpty(req);
        }

        else if(ex instanceof PostContentTooLongException) {
            return handlePostTooLong(req);
        }

        else if(ex instanceof UnauthorizedPostDeleteException) {
            return handleUnauthorizedPostDelete(req);
        }

        return new ProblemDetails(
                "Unknown Error",
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                "An unexpected error has occurred.",
                req.getRequestURI()
        );
    }

    private static ProblemDetails handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpServletRequest request) {
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

        return new ProblemDetails(title, HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), detail, request.getRequestURI());
    }


    private static ProblemDetails handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpServletRequest request) {
        String title = "Field not provided";
        String detail = String.format("The field '%s' was not provided.", ex.getParameterName());

        return new ProblemDetails(title, HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), detail, request.getRequestURI());
    }

    private static ProblemDetails handleUserNotFoundException(HttpServletRequest req) {
        String title = "User not found";
        String detail = "User not found for the provided ID";
        return new ProblemDetails(
                title,
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                detail,
                req.getRequestURI()
        );
    }

    private static ProblemDetails handleDataIntegrityViolation(HttpServletRequest request) {
        String title = "Field integrity violation";
        String detail = "Integrity violation detected in the database.";

        return new ProblemDetails(
                title,
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                detail,
                request.getRequestURI()
        );
    }

    private static ProblemDetails handleEmailAlreadyRegistered(EmailAlreadyRegisteredException ex, HttpServletRequest req) {
        String title = "E-mail already registered";
        String detail = "The provided email address is already associated with another account.";
        return new ProblemDetails(
                title,
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.getReasonPhrase(),
                detail,
                req.getRequestURI()
        );
    }

    private static ProblemDetails handleUsernameAlreadyRegistered(UsernameAlreadyRegisteredException ex, HttpServletRequest req) {
        String title = "Username already taken";
        String detail = "The provided username is already in use";

        return new ProblemDetails(
                title,
                HttpStatus.CONFLICT.value(),
                HttpStatus.RESET_CONTENT.getReasonPhrase(),
                detail,
                req.getRequestURI()
        );
    }

    private static ProblemDetails handleUserAlreadyActive(HttpServletRequest req) {
        String title = "User already active";
        String detail = "The user is already in an active state.";
        return new ProblemDetails(
                title,
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                detail,
                req.getRequestURI()
        );
    }

    private static ProblemDetails handleUserAlreadyInactive(HttpServletRequest req) {
        String title = "User already inactive";
        String detail = "The user is already in an inactive state.";
        return new ProblemDetails(
                title,
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                detail,
                req.getRequestURI()
        );
    }

    private static ProblemDetails handleAlreadyNotFollowingUser(HttpServletRequest req) {
        String title = "User Not Being Followed";
        String detail = "You are not following this user.";
        return new ProblemDetails(
                title,
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.getReasonPhrase(),
                detail,
                req.getRequestURI()
        );
    }

    private static ProblemDetails handleAlreadyFollowingUser(HttpServletRequest req) {
        String title = "User Already Followed";
        String detail = "You are already following this user.";
        return new ProblemDetails(
                title,
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.getReasonPhrase(),
                detail,
                req.getRequestURI()
        );
    }


    private static ProblemDetails handleSelfFollow(HttpServletRequest req) {
        String title = "Invalid Follow action";
        String detail = "You can't follow yourself!";
        return new ProblemDetails(
                title,
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                detail,
                req.getRequestURI()
        );
    }

    private static ProblemDetails handleSelfUnfollow(HttpServletRequest req) {
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

    private static ProblemDetails handlePostAlreadyLike(HttpServletRequest req) {
        String title = "Post Already Liked!";
        String detail = "You have already liked this post.";

        return new ProblemDetails(
                title,
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.getReasonPhrase(),
                detail,
                req.getRequestURI()
        );
    }

    private static ProblemDetails  handlePostAlreadyNotLiked(HttpServletRequest req) {
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

    private static ProblemDetails handlePostNotFound(HttpServletRequest req) {
        String title = "Post not found!";
        String detail = "The requested post does not exist or was deleted.";

        return new ProblemDetails(
                title,
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                detail,
                req.getRequestURI()
        );
    }

    private static ProblemDetails handleContentPostEmpty(HttpServletRequest req) {
        String title = "Post Content Missing";
        String detail = "The content of the post cannot be empty. Please provide content before submitting.";

        return new ProblemDetails(
                title,
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                detail,
                req.getRequestURI()
        );
    }

    private static ProblemDetails handlePostTooLong (HttpServletRequest req) {
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

    private static ProblemDetails handleUnauthorizedPostDelete (HttpServletRequest req) {
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


    private static ProblemDetails handleConversionFailed(ConversionFailedException ex, HttpServletRequest request) {
        String title = "Invalid field value provided";
        String invalidValue = Optional.ofNullable(ex.getValue())
                .map(Object::toString)
                .orElse(VALUE_NOT_INFORMED);
        String requiredType = Optional.of(ex.getTargetType())
                .map(typeDescriptor -> typeDescriptor.getType().getSimpleName())
                .orElse(UNKNOWN_TYPE);
        String detail = String.format("The value '%s' provided is invalid. A value of type '%s' was expected.", invalidValue, requiredType);

        return new ProblemDetails(title, HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), detail, request.getRequestURI());
    }

}
