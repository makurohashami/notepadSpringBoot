package com.kotyk.notepad.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globalExceptionHandler(WebRequest request) {
        Error error = new Error(
                new Date(),
                "All bad. Backend error",
                request.getDescription(false));

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> methodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
        Error errorDetails = new Error(
                new Date(),
                "Validation failed: " + ex.getBindingResult().getFieldError().getField() + " " + ex.getBindingResult().getFieldError().getDefaultMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<?> methodUsernameAlreadyExistsException(WebRequest request) {
        Error error = new Error(
                new Date(),
                "This username is already taken",
                request.getDescription(false));

        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<?> methodEmailAlreadyExistsException(WebRequest request) {
        Error error = new Error(
                new Date(),
                "This email is already taken",
                request.getDescription(false));

        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> methodResourceNotFoundException(WebRequest request) {
        Error error = new Error(
                new Date(),
                "Resource not found",
                request.getDescription(false));
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> methodHttpMessageNotReadableException(WebRequest request) {
        Error error = new Error(
                new Date(),
                "Bad request",
                request.getDescription(false));
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

}
