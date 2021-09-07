package com.contoso.api.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.sql.Timestamp;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger logger = LogManager.getLogger(CustomExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> globalExceptionHandler(Exception exception, WebRequest request) {
        logger.error("General Exception stacktrace :: "+ ExceptionUtils.getStackTrace(exception));
        ErrorDetails errorDetails = new ErrorDetails(new Timestamp(System.currentTimeMillis()), exception.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(GraphException.class)
    public final ResponseEntity<Object> graphExceptionHandler(GraphException graphException) {
        logger.error("Exception stacktrace For Graph Services :: "+ ExceptionUtils.getStackTrace(graphException));
        ErrorDetails errorDetails = new ErrorDetails(new Timestamp(System.currentTimeMillis()),
                graphException.getMessage(),  graphException.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public final ResponseEntity<Object> graphExceptionHandler(ResourceNotFoundException resourceNotFoundException) {
        logger.error("Exception stacktrace For Resource Services :: "+ ExceptionUtils.getStackTrace(resourceNotFoundException));
        ErrorDetails errorDetails = new ErrorDetails(new Timestamp(System.currentTimeMillis()),
                resourceNotFoundException.getMessage(),  resourceNotFoundException.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @Data
    @AllArgsConstructor
    static class ErrorDetails {
        private Timestamp timestamp;
        private String message;
        private String details;
    }
}
