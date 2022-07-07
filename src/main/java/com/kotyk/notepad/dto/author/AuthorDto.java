package com.kotyk.notepad.dto.author;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kotyk.notepad.dto.note.NoteDto;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Collection;

public class AuthorDto {

    @Min(0)
    @Schema(description = "Id of author", example = "1", required = true)
    public Integer id;
    @NotBlank
    @Schema(description = "Username of author", example = "mike1999", required = true)
    public String username;
    @NotBlank
    @Schema(description = "Name of author", example = "M'White", required = true)
    public String name;
    @Email
    @NotNull
    @Schema(description = "email of author", example = "mike1999@gmail.com", required = true)
    public String email;
    @JsonIgnore
    //@Schema(description = "Collection notes of author", required = true)
    public Collection<NoteDto> notes;
    @JsonIgnore
    public Boolean isDeleted = Boolean.FALSE;

}
