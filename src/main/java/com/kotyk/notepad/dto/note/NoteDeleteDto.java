package com.kotyk.notepad.dto.note;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.Instant;
import java.util.Date;

public class NoteDeleteDto {

    public NoteDeleteDto(String title) {
        this.title = title;
    }

    public String title;
    public String message = "Note was deleted successfully";
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    public Date date = Date.from(Instant.now());

}
