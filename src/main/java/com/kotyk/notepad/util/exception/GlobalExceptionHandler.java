package com.kotyk.notepad.util.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@Log4j2
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globalExceptionHandler(WebRequest request) {
        Error error = new Error(
                new Date(),
                "All bad. Backend error",
                request.getDescription(false));

        log.info("Exception: All bad. Backend error");
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> methodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
        Error errorDetails = new Error(
                new Date(),
                "Validation failed: " + ex.getBindingResult().getFieldError().getField() + " " + ex.getBindingResult().getFieldError().getDefaultMessage(),
                request.getDescription(false));

        log.info("Exception: " + "Validation failed: " + ex.getBindingResult().getFieldError().getField() + " " + ex.getBindingResult().getFieldError().getDefaultMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<?> methodUsernameAlreadyExistsException(WebRequest request) {
        Error error = new Error(
                new Date(),
                "This username is already taken",
                request.getDescription(false));

        log.info("Exception: This username is already taken");
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<?> methodEmailAlreadyExistsException(WebRequest request) {
        Error error = new Error(
                new Date(),
                "This email is already taken",
                request.getDescription(false));

        log.info("Exception: This email is already taken");
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> methodResourceNotFoundException(WebRequest request) {
        Error error = new Error(
                new Date(),
                "Resource not found",
                request.getDescription(false));

        log.info("Exception: Resource not found");
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> methodHttpMessageNotReadableException(WebRequest request) {
        Error error = new Error(
                new Date(),
                "Bad request",
                request.getDescription(false));

        log.info("Exception: Bad request");
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

}
