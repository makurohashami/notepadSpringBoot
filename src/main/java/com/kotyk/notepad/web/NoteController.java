package com.kotyk.notepad.web;

import com.kotyk.notepad.domain.NoteStatus;
import com.kotyk.notepad.domain.NoteType;
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
        log.debug("Controller: createNote() - start: note = {}", noteDto);
        var note = NoteMapper.INSTANCE.dtoToNote(noteDto);
        var dto = AuthorMapper.INSTANCE.authorToReadDto(noteService.create(username, note));
        log.debug("Controller: createNote() - end: note = {}", dto);

        return dto;
    }

    @PatchMapping("/{username}/notes/{id}")
    @ResponseStatus(HttpStatus.OK)
    public NoteDto patchNote(@PathVariable("username") String username,
                             @PathVariable("id") Integer id,
                             @RequestBody NoteDto noteDto) {
        log.debug("Controller: patchNote() Controller - start: username = {}, noteDto = {}, id = {}", username, noteDto, id);
        var note = NoteMapper.INSTANCE.dtoToNote(noteDto);
        var dto = NoteMapper.INSTANCE.noteToDto(noteService.update(username, id, note));
        log.debug("Controller: patchNote() - end: note = {}", dto);

        return dto;


    }

    @PatchMapping("/{username}/notes/{id}/setDone")
    @ResponseStatus(HttpStatus.OK)
    public NoteReadDto setIsDone(@PathVariable("username") String username, @PathVariable("id") Integer id) {
        log.debug("Controller: setIsDone() - start: username = {}, id = {}", username, id);
        var note = noteService.updateSetIsDone(username, id);
        var dto = NoteMapper.INSTANCE.noteToRead(note);
        log.debug("Controller: setIsDone() - end: note = {}", dto);

        return dto;
    }

    @PatchMapping("/{username}/notes/{id}/setNotDone")
    @ResponseStatus(HttpStatus.OK)
    public NoteReadDto setNotDone(@PathVariable("username") String username, @PathVariable("id") Integer id) {
        log.debug("Controller: setNotDone() - start: username = {}, id = {}", username, id);
        var note = noteService.updateSetNotDone(username, id);
        var dto = NoteMapper.INSTANCE.noteToRead(note);
        log.debug("Controller: setNotDone() - end: note = {}", dto);

        return dto;
    }

    @DeleteMapping("/{username}/notes/{id}")
    @ResponseStatus(HttpStatus.GONE)
    public NoteDeleteDto deleteNote(@PathVariable("username") String username, @PathVariable("id") Integer id) {
        log.debug("Controller: deleteNote() - start: username = {}, id = {}", username, id);
        noteService.delete(username, id);
        log.debug("Controller: deleteNote() - end: Note was deleted successfully");
        return new NoteDeleteDto(id);
    }

    @GetMapping("/{username}/notes/{id}")
    @ResponseStatus(HttpStatus.OK)
    public NoteReadDto viewNote(@PathVariable("username") String username, @PathVariable("id") Integer id) {
        log.debug("Controller: viewNote() - start: username = {}, id = {}", username, id);
        var note = noteService.read(username, id);
        var dto = NoteMapper.INSTANCE.noteToRead(note);
        log.debug("Controller: viewNote() - end: note = {}", dto);

        return dto;
    }

    @GetMapping("/{username}/notes/title/{title}")
    @ResponseStatus(HttpStatus.OK)
    public Collection<NoteReadDto> viewNotesByTitle(@PathVariable("username") String username, @PathVariable("title") String title) {
        log.debug("Controller: viewNotesByTitle()- start: username = {}, title = {}", username, title);
        var notes = noteService.readByTitle(username, title);
        var dto = NoteMapper.INSTANCE.notesToReads(notes);
        log.debug("Controller: viewNotesByTitle() - end: notes = {}", dto);

        return dto;
    }

    @GetMapping("/{username}/notes")
    @ResponseStatus(HttpStatus.OK)
    public Collection<NoteDto> viewNotes(@PathVariable("username") String username) {
        log.debug("Controller: viewNotes() - start: username = {}", username);
        var notes = noteService.readAll(username);
        var dto = NoteMapper.INSTANCE.notesToDtos(notes);
        log.debug("Controller: viewNotes() - end: notes = {}", dto);

        return dto;
    }

    @GetMapping("/{username}/notes/status/{status}")
    @ResponseStatus(HttpStatus.OK)
    public Collection<NoteReadDto> viewNotesByStatus(@PathVariable("username") String username, @PathVariable("status") NoteStatus status) {
        log.debug("Controller: viewNotesByStatus() - start: username = {}, status = {}", username, status);
        var notes = noteService.readByStatus(username, status);
        var dto = NoteMapper.INSTANCE.notesToReads(notes);
        log.debug("Controller: viewNotesByStatus() - end: notes = {}", dto);

        return dto;
    }

    @GetMapping("/{username}/notes/type/{type}")
    @ResponseStatus(HttpStatus.OK)
    public Collection<NoteReadDto> viewNotesByType(@PathVariable("username") String username, @PathVariable("type") NoteType type) {
        log.debug("Controller: viewNotesByType() - start: username = {}, type = {}", username, type);
        var notes = noteService.readByType(username, type);
        var dto = NoteMapper.INSTANCE.notesToReads(notes);
        log.debug("Controller: viewNotesByType() - end: notes = {}", dto);

        return dto;
    }


    @GetMapping("/{username}/notes/expired")
    @ResponseStatus(HttpStatus.OK)
    public Collection<NoteReadDto> viewExpiredNotes(@PathVariable String username) {
        log.debug("Controller: viewExpiredNotes() - start: username = {}", username);
        var notes = noteService.readAllExpired(username);
        var dto = NoteMapper.INSTANCE.notesToReads(notes);
        log.debug("viewExpiredNotes() Controller - end: notes = {}", dto);

        return dto;
    }

    @GetMapping("/{username}/notes/notExpired")
    @ResponseStatus(HttpStatus.OK)
    public Collection<NoteReadDto> viewNotExpiredNotes(@PathVariable String username) {
        log.debug("Controller: viewNotExpiredNotes() - start: username = {}", username);
        var notes = noteService.readAllNotExpired(username);
        var dto = NoteMapper.INSTANCE.notesToReads(notes);
        log.debug("Controller: viewNotExpiredNotes() - end: notes = {}", dto);

        return dto;
    }

    @GetMapping("/{username}/notes/done")
    @ResponseStatus(HttpStatus.OK)
    public Collection<NoteReadDto> viewAllDone(@PathVariable("username") String username) {
        log.debug("Controller: viewAllDone() - start: username = {}", username);
        var notes = noteService.readAllIsDone(username);
        var dto = NoteMapper.INSTANCE.notesToReads(notes);
        log.debug("Controller: viewAllDone() - end: notes = {}", dto);

        return dto;
    }

    @GetMapping("/{username}/notes/notDone")
    @ResponseStatus(HttpStatus.OK)
    public Collection<NoteReadDto> viewAllNotDone(@PathVariable("username") String username) {
        log.debug("Controller: viewAllNotDone() - start: username = {}", username);
        var notes = noteService.readAllNotDone(username);
        var dto = NoteMapper.INSTANCE.notesToReads(notes);
        log.debug("Controller: viewAllNotDone() - end: notes = {}", dto);

        return dto;
    }

}
