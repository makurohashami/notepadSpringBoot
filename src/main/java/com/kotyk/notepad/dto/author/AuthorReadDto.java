package com.kotyk.notepad.dto.author;

import com.kotyk.notepad.dto.note.NoteReadDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.ToString;

import java.util.Collection;

@ToString
public class AuthorReadDto {

    @Schema(description = "Name of author", example = "M'White", required = true)
    public String name;
    @Schema(description = "email of author", example = "mike1999@gmail.com", required = true)
    public String email;
    @Schema(description = "Collection notes of author", required = true)
    public Collection<NoteReadDto> notes;

}
