package com.kotyk.notepad.dto.author;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;
import java.util.Date;

public class AuthorDeleteDto {

    public AuthorDeleteDto(String username) {
        this.username = username;
    }

    @Schema(description = "Username of author", example = "mike1999", required = true)
    public String username;
    @Schema(description = "Author was deleted successfully", required = true)
    public String message = "Author was deleted successfully";
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @Schema(description = "Date of deletion", example = "2022-11-15 12:36", required = true)
    public Date date = Date.from(Instant.now());
}
