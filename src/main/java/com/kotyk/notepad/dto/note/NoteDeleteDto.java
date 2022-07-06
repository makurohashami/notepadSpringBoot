package com.kotyk.notepad.dto.note;

import java.time.Instant;
import java.util.Date;

public class NoteDeleteDto {

    public NoteDeleteDto(String title) {
        this.title = title;
    }

    public String title;
    public String message = "Note was deleted successfully";
    public Date date = Date.from(Instant.now());

}
