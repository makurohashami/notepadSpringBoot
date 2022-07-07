package com.kotyk.notepad.dto.note;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.Instant;
import java.util.Date;

public class NoteDeleteDto {

    public NoteDeleteDto(Integer id) {
        this.id = id;
    }

    public Integer id;
    public String message = "Note was deleted successfully";
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    public Date date = Date.from(Instant.now());

}
