package com.kotyk.notepad.dto.note;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kotyk.notepad.domain.NoteStatus;
import com.kotyk.notepad.domain.NoteType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.Date;

@ToString
public class NoteDto {

    @Min(0)
    @Schema(description = "Id of note", example = "1", required = true)
    public Integer id;
    @NotBlank
    @Schema(description = "Title of note", example = "Buy bread", required = true)
    public String title;
    @Schema(description = "Description of note", example = "Buy in walmart", required = true)
    public String description;
    @Schema(description = "Type of note", example = "HOME", required = true)
    public NoteType type = NoteType.NONE;
    @Schema(description = "Status of note", example = "TODO", required = true)
    public NoteStatus status = NoteStatus.NONE;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @Schema(description = "Date of creation", example = "2022-11-15 12:36", required = true)
    //todo fix time
    public Date timeOfCreation = Date.from(Instant.now());
    @JsonIgnore
    public Boolean isDeleted = Boolean.FALSE;

}
