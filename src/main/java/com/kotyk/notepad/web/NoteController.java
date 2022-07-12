package com.kotyk.notepad.web;

import com.kotyk.notepad.domain.NoteStatus;
import com.kotyk.notepad.dto.author.AuthorReadDto;
import com.kotyk.notepad.dto.note.NoteDeleteDto;
import com.kotyk.notepad.dto.note.NoteDto;
import com.kotyk.notepad.dto.note.NoteReadDto;
import com.kotyk.notepad.service.NoteService;
import com.kotyk.notepad.util.config.AuthorMapper;
import com.kotyk.notepad.util.config.NoteMapper;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/notepad/authors", produces = MediaType.APPLICATION_JSON_VALUE)
@Log4j2
public class NoteController implements NoteSwagger {

    private final NoteService noteService;


    @PostMapping("/{username}/notes")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthorReadDto createNote(@PathVariable String username, @RequestBody NoteDto noteDto) {
        log.debug("createNote() Controller - start: noteDto = {}", noteDto);
        var note = NoteMapper.INSTANCE.dtoToNote(noteDto);
        var dto = AuthorMapper.INSTANCE.authorToReadDto(noteService.create(username, note));
        log.debug("createNote() Controller - end: noteDto = {}", dto);

        return dto;
    }

    @GetMapping("{username}/notes/{id}")
    @ResponseStatus(HttpStatus.OK)
    public NoteReadDto viewNote(@PathVariable("username") String username, @PathVariable("id") Integer id) {
        log.debug("viewNote() Controller - start: username = {}, id = {}", username, id);
        var note = noteService.read(username, id);
        var dto = NoteMapper.INSTANCE.noteToRead(note);
        log.debug("viewNote() Controller - end: Note = {}", dto);

        return dto;
    }

    @GetMapping("{username}/notes/title/{title}")
    @ResponseStatus(HttpStatus.OK)
    public Collection<NoteReadDto> viewNotesByTitle(@PathVariable("username") String username, @PathVariable("title") String title) {
        log.debug("viewNotesByTitle() Controller - start: username = {}, title = {}", username, title);
        var note = noteService.readByTitle(username, title);
        var dto = NoteMapper.INSTANCE.notesToReads(note);
        log.debug("viewNotesByTitle() Controller - end: notes count = {}", dto.size());

        return dto;
    }

    @GetMapping("{username}/notes")
    @ResponseStatus(HttpStatus.OK)
    public Collection<NoteDto> viewNotes(@PathVariable("username") String username) {
        log.debug("viewNotes() Controller - start:");
        var notes = noteService.readAll(username);
        var dto = NoteMapper.INSTANCE.notesToDtos(notes);
        log.debug("viewNotes() Controller - end: notes count = {}", dto.size());

        return dto;
    }

    @PatchMapping("{username}/notes/{id}")
    @ResponseStatus(HttpStatus.OK)
    public NoteDto patchNote(@PathVariable("username") String username,
                             @PathVariable("id") Integer id,
                             @RequestBody /*@Valid*/ NoteDto noteDto) {
        log.debug("patchNote() Controller - start: username = {}, noteDto = {}, id = {}", username, noteDto, id);
        var note = NoteMapper.INSTANCE.dtoToNote(noteDto);
        var dto = NoteMapper.INSTANCE.noteToDto(noteService.update(username, id, note));
        log.debug("patchNote() Controller - end: Note = {}", dto);

        return dto;


    }

    @DeleteMapping("{username}/notes/{id}")
    @ResponseStatus(HttpStatus.GONE)
    public NoteDeleteDto deleteNote(@PathVariable("username") String username, @PathVariable("id") Integer id) {
        log.debug("deleteNote() Controller - start: username = {}, id = {}", username, id);
        noteService.delete(username, id);
        log.debug("deleteNote() Controller - end: Note was deleted successfully");
        return new NoteDeleteDto(id);
    }

    @GetMapping("{username}/notes/status/{status}")
    @ResponseStatus(HttpStatus.OK)
    public Collection<NoteReadDto> viewNotesByStatus(@PathVariable("username") String username, @PathVariable("status") NoteStatus status) {
        //todo fix exception
        var note = noteService.readByStatus(username, status);
        var dto = NoteMapper.INSTANCE.notesToReads(note);

        return dto;
    }

}
