package com.kotyk.notepad.dto.author;

import java.time.Instant;
import java.util.Date;

public class AuthorDeleteDto {

    public AuthorDeleteDto(String username) {
        this.username = username;
    }

    public String username;
    public String message = "Author was deleted successfully";
    public Date date = Date.from(Instant.now());
}
