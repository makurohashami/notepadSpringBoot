package com.kotyk.notepad.util.config;

import com.kotyk.notepad.domain.Note;
import com.kotyk.notepad.dto.note.NoteDto;
import com.kotyk.notepad.dto.note.NoteReadDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Collection;

@Mapper
public interface NoteMapper {

    NoteMapper INSTANCE = Mappers.getMapper(NoteMapper.class);

    Note dtoToNote(NoteDto dto);

    NoteDto noteToDto(Note note);

    NoteReadDto noteToRead(Note note);

    Collection<NoteReadDto> notesToReads(Collection<Note> notes);

    Collection<NoteDto> notesToDtos(Collection<Note> notes);

}