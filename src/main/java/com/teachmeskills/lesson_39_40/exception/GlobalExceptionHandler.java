package com.teachmeskills.lesson_39_40.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(AuthenticationException.class)
    public ModelAndView handleAuthenticationException(
            AuthenticationException ex,
            HttpServletRequest request) {

        logger.error("""
                🔐 Authentication Error
                URL -> {}
                Method -> {}
                User -> {}
                Error > {}
                Stacktrace ->""",
                request.getRequestURL(),
                request.getMethod(),
                request.getRemoteUser(),
                ex.getMessage(),
                ex
        );

        return buildErrorModel(
                "Authentication Error",
                "Invalid credentials",
                HttpStatus.UNAUTHORIZED,
                "error-auth"
        );
    }

    @ExceptionHandler(RegistrationException.class)
    public ModelAndView handleRegistrationException(
            RegistrationException ex,
            HttpServletRequest request) {

        logger.error("""
                📝 Registration Failed
                URL -> {}
                Params -> {}
                Error -> {}
                Stacktrace ->""",
                request.getRequestURL(),
                request.getParameterMap(),
                ex.getMessage(),
                ex
        );

        return buildErrorModel(
                "Registration Error",
                ex.getMessage(),
                HttpStatus.BAD_REQUEST,
                "error-registration"
        );
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ModelAndView handleUserNotFoundException(
            UserNotFoundException ex,
            HttpServletRequest request) {

        logger.warn("""
                🔎 User Not Found
                URL -> {}
                Query -> {}
                Error -> {}""",
                request.getRequestURL(),
                request.getQueryString(),
                ex.getMessage()
        );

        return buildErrorModel(
                "User Not Found",
                ex.getMessage(),
                HttpStatus.NOT_FOUND,
                "error-user"
        );
    }

    @ExceptionHandler({PasswordHashingException.class, SaltGenerationException.class})
    public ModelAndView handleSecurityExceptions(
            RuntimeException ex,
            HttpServletRequest request) {

        logger.error("""
                🛡️ Security Error
                URL -> {}
                Headers -> {}
                Error -> {}
                Stacktrace ->""",
                request.getRequestURL(),
                request.getHeaderNames(),
                ex.getMessage(),
                ex
        );

        return buildErrorModel(
                "Security Error",
                "Internal security processing error",
                HttpStatus.INTERNAL_SERVER_ERROR,
                "error-security"
        );
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleAllExceptions(
            Exception ex,
            HttpServletRequest request) {

        logger.error("""
                ⚠️ Unhandled Exception
                URL -> {}
                Method -> {}
                Session -> {}
                Error -> {}
                Full Stacktrace ->""",
                request.getRequestURL(),
                request.getMethod(),
                request.getSession().getId(),
                ex.getMessage(),
                ex
        );

        return buildErrorModel(
                "Server Error",
                "Internal application error",
                HttpStatus.INTERNAL_SERVER_ERROR,
                "error"
        );
    }

    private ModelAndView buildErrorModel(
            String errorTitle,
            String errorMessage,
            HttpStatus status,
            String viewName) {

        logger.debug("Building error model -> {} | {}", errorTitle, errorMessage);

        ModelAndView model = new ModelAndView();
        model.addObject("errorTitle", errorTitle);
        model.addObject("errorMessage", errorMessage);
        model.addObject("statusCode", status.value());
        model.setStatus(status);
        model.setViewName(viewName);
        return model;
    }
}