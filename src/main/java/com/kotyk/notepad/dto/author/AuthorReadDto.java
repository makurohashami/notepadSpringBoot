package com.kotyk.notepad.dto.author;

import com.kotyk.notepad.dto.note.NoteReadDto;

import java.util.Collection;

public class AuthorReadDto {

    public String name;
    public String email;
    public Collection<NoteReadDto> notes;

}
