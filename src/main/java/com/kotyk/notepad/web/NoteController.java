package com.kotyk.notepad.web;

import com.kotyk.notepad.dto.author.AuthorReadDto;
import com.kotyk.notepad.dto.note.NoteDeleteDto;
import com.kotyk.notepad.dto.note.NoteDto;
import com.kotyk.notepad.dto.note.NoteReadDto;
import com.kotyk.notepad.service.NoteService;
import com.kotyk.notepad.util.config.AuthorMapper;
import com.kotyk.notepad.util.config.NoteMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/notepad/authors", produces = MediaType.APPLICATION_JSON_VALUE)
public class NoteController implements NoteSwagger {

    private final NoteService noteService;


    @PostMapping("/{username}/notes")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthorReadDto createNote(@PathVariable String username, @RequestBody NoteDto noteDto) {
        var note = NoteMapper.INSTANCE.dtoToNote(noteDto);
        var dto = AuthorMapper.INSTANCE.authorToReadDto(noteService.create(username, note));

        return dto;
    }

    @GetMapping("{username}/notes/{id}")
    @ResponseStatus(HttpStatus.OK)
    public NoteReadDto viewNote(@PathVariable("username") String username, @PathVariable("id") Integer id) {
        var note = noteService.read(username, id);
        var dto = NoteMapper.INSTANCE.noteToRead(note);

        return dto;
    }

    @GetMapping("{username}/notes/title/{title}")
    @ResponseStatus(HttpStatus.OK)
    public Collection<NoteReadDto> viewNotesByTitle(@PathVariable("username") String username, @PathVariable("title") String title) {
        var note = noteService.readByTitle(username, title);
        var dto = NoteMapper.INSTANCE.notesToReads(note);

        return dto;
    }

    @GetMapping("{username}/notes")
    @ResponseStatus(HttpStatus.OK)
    public Collection<NoteDto> viewNotes(@PathVariable("username") String username) {
        var notes = noteService.readAll(username);
        var dto = NoteMapper.INSTANCE.notesToDtos(notes);

        return dto;
    }

    @PatchMapping("{username}/notes/{id}")
    @ResponseStatus(HttpStatus.OK)
    public NoteDto patchNote(@PathVariable("username") String username,
                             @PathVariable("id") Integer id,
                             @RequestBody /*@Valid*/ NoteDto noteDto) {
        var note = NoteMapper.INSTANCE.dtoToNote(noteDto);
        var dto = NoteMapper.INSTANCE.noteToDto(noteService.update(username, id, note));

        return dto;


    }

    @DeleteMapping("{username}/notes/{id}")
    @ResponseStatus(HttpStatus.GONE)
    public NoteDeleteDto deleteNote(@PathVariable("username") String username, @PathVariable("id") Integer id) {
        noteService.delete(username, id);
        return new NoteDeleteDto(id);
    }

}
