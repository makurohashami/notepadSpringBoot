package com.kotyk.notepad.dto.note;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kotyk.notepad.domain.NoteStatus;
import com.kotyk.notepad.domain.NoteType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.ToString;

import java.util.Date;

@ToString
public class NoteReadDto {

    @Schema(description = "Title of note", example = "Buy bread", required = true)
    public String title;
    @Schema(description = "Description of note", example = "Buy in walmart", required = true)
    public String description;
    @Schema(description = "Type of note", example = "HOME", required = true)
    public NoteType type;
    @Schema(description = "Status of note", example = "TODO", required = true)
    public NoteStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @Schema(description = "Date of creation", example = "2022-11-15 12:36", required = true)
    public Date timeOfCreation;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @Schema(description = "End time", example = "2022-11-15 12:36", required = true)
    public Date endTime;
    @Schema(description = "Note completion status", example = "true", required = true)
    public Boolean isDone;

}
