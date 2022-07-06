package com.kotyk.notepad.dto.note;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kotyk.notepad.domain.NoteStatus;
import com.kotyk.notepad.domain.NoteType;

import java.util.Date;

public class NoteReadDto {

    public String title;
    public String description;
    public NoteType type;
    public NoteStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    public Date timeOfCreation;

}
