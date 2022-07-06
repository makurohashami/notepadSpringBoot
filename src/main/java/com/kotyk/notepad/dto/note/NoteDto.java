package com.kotyk.notepad.dto.note;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kotyk.notepad.domain.NoteStatus;
import com.kotyk.notepad.domain.NoteType;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.Date;

public class NoteDto {

    @Min(0)
    public Integer id;
    @NotBlank
    public String title;
    public String description;
    public NoteType type = NoteType.NONE;
    public NoteStatus status = NoteStatus.NONE;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    public Date timeOfCreation = Date.from(Instant.now());
    @JsonIgnore
    public Boolean isDeleted = Boolean.FALSE;

}
