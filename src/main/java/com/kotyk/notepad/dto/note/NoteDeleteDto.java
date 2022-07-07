package com.kotyk.notepad.dto.note;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;
import java.util.Date;

public class NoteDeleteDto {

    public NoteDeleteDto(Integer id) {
        this.id = id;
    }

    @Schema(description = "Id of note", example = "1", required = true)
    public Integer id;
    @Schema(description = "Message", required = true)
    public String message = "Note was deleted successfully";
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @Schema(description = "Date of deletion", example = "2022-11-15 12:36", required = true)
    public Date date = Date.from(Instant.now());

}
