package com.kotyk.notepad.dto.author;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kotyk.notepad.dto.note.NoteDto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.Collection;

public class AuthorDto {

    @Min(0)
    public Integer id;
    @NotBlank
    public String username;
    @NotBlank
    public String name;
    @Email
    public String email;
    @JsonIgnore
    public Collection<NoteDto> notes;
    @JsonIgnore
    public Boolean isDeleted;

}
