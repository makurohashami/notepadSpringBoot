package com.kotyk.notepad.util.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.Instant;
import java.util.Date;

@Log4j2
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globalExceptionHandler(WebRequest request, Exception ex) {
        Error error = new Error(
                Date.from(Instant.now()),
                "All bad. Backend error" + " Class: " + ex.getClass().getName() + " Message: " + ex.getMessage(),
                request.getDescription(false));

        log.info("Exception: " + error.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> methodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
        Error error = new Error(
                Date.from(Instant.now()),
                "Validation failed: " + ex.getBindingResult().getFieldError().getField() + " " + ex.getBindingResult().getFieldError().getDefaultMessage(),
                request.getDescription(false));

        log.info("Exception: " + error.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<?> methodUsernameAlreadyExistsException(WebRequest request) {
        Error error = new Error(
                Date.from(Instant.now()),
                "This username is already taken",
                request.getDescription(false));

        log.info("Exception: " + error.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<?> methodEmailAlreadyExistsException(WebRequest request) {
        Error error = new Error(
                Date.from(Instant.now()),
                "This email is already taken",
                request.getDescription(false));

        log.info("Exception: " + error.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> methodResourceNotFoundException(WebRequest request) {
        Error error = new Error(
                Date.from(Instant.now()),
                "Resource not found",
                request.getDescription(false));

        log.info("Exception: " + error.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> methodHttpMessageNotReadableException(WebRequest request) {
        Error error = new Error(
                Date.from(Instant.now()),
                "Bad request",
                request.getDescription(false));

        log.info("Exception: " + error.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> methodMethodArgumentTypeMismatchException(WebRequest request) {
        Error error = new Error(
                Date.from(Instant.now()),
                "Bad ENUM argument",
                request.getDescription(false));

        log.info("Exception: " + error.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<?> methodHttpRequestMethodNotSupportedException(WebRequest request) {
        Error error = new Error(
                Date.from(Instant.now()),
                "Method not exists",
                request.getDescription(false));

        log.info("Exception: " + error.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

}
