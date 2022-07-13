package com.kotyk.notepad.util.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.Date;

@Getter
public class Error {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @Schema(description = "Date of error", example = "2022-11-15 12:36", required = true)
    private Date timestamp;
    @Schema(description = "Message", example = "Resource not found", required = true)
    private String message;
    @Schema(description = "uri", example = "uri=/notepad/authors/bill/notes/4", required = true)
    private String details;

    public Error(Date timestamp, String message, String details) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }
}
