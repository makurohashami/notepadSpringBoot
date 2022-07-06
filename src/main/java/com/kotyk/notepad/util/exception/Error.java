package com.kotyk.notepad.util.exception;

import lombok.Getter;

import java.util.Date;

@Getter
public class Error {
    private Date timestamp;
    private String message;
    private String details;

    public Error(Date timestamp, String message, String details) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }
}
