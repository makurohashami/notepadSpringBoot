package com.kotyk.notepad.dto.author;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.Instant;
import java.util.Date;

public class AuthorDeleteDto {

    public AuthorDeleteDto(String username) {
        this.username = username;
    }

    public String username;
    public String message = "Author was deleted successfully";
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    public Date date = Date.from(Instant.now());
}
