package com.kotyk.notepad.service;

import com.kotyk.notepad.domain.Author;
import com.kotyk.notepad.domain.Note;
import com.kotyk.notepad.domain.NoteStatus;
import com.kotyk.notepad.domain.NoteType;
import com.kotyk.notepad.repository.AuthorRepository;
import com.kotyk.notepad.repository.NoteRepository;
import com.kotyk.notepad.util.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Service
@AllArgsConstructor
@Log4j2
public class NoteServiceBean implements NoteService {

    private final AuthorRepository authorRepository;
    private final AuthorServiceBean authorServiceBean;
    private final NoteRepository noteRepository;

    @Override
    public Author create(String username, Note note) {
        log.info("create() - start: username = {}, title = {}", username, note.getTitle());
        trimNote(note);
        var author = authorServiceBean.getAuthorByUsername(username);
        note.setNoteAuthor(author);
        author.getNotes().add(note);
        var saved = authorRepository.save(author);
        log.info("create() - end: author's username = {}", saved.getUsername());

        return saved;
    }

    @Override
    public Note read(String username, Integer id) {
        log.info("read() - start: username = {}, id = {}", username, id);
        var note = getNoteByIdAndAuthorUsername(username, id);
        log.info("read() - end: id = {}", note.getId());

        return note;
    }

    @Override
    public Collection<Note> readByTitle(String username, String title) {
        log.info("readByTitle() - start: username = {}, title = {}", username, title);
        var notes = noteRepository.findByTitle(username, title);
        log.info("readByTitle() - end: notes count = {}", notes.size());

        return notes;
    }

    @Override
    public Collection<Note> readAll(String username) {
        log.info("readAll() - start: username = {}", username);
        var notes = noteRepository.findAll(username);
        log.info("readAll() - end: notes count = {}", notes.size());

        return notes;
    }

    @Override
    public Note update(String username, Integer id, Note note) {
        log.info("update() - start: username = {}, id = {}", username, id);
        var newNote = getNoteByIdAndAuthorUsername(username, id);
        trimNote(note);

        if (isNotNull(note.getTitle()) && !note.getTitle().isBlank()) {
            newNote.setTitle(note.getTitle());
        }
        if (isNotNull(note.getDescription()) && !note.getDescription().isBlank()) {
            newNote.setDescription(note.getDescription());
        }
        if (isNotNull(note.getType())) {
            newNote.setType(note.getType());
        }
        if (isNotNull(note.getStatus())) {
            newNote.setStatus(note.getStatus());
        }
        if (isNotNull(note.getEndTime())) {
            newNote.setEndTime(note.getEndTime());
        }
        if (isNotNull(note.getIsDone())) {
            newNote.setIsDone(note.getIsDone());
        }

        log.info("update() - end: id = {}", newNote.getId());
        return noteRepository.save(newNote);
    }

    @Override
    public void delete(String username, Integer id) {
        log.info("delete() - start: username = {}, id = {}", username, id);
        var note = getNoteByIdAndAuthorUsername(username, id);
        note.setIsDeleted(Boolean.TRUE);
        noteRepository.save(note);
        log.info("delete() - end: isDeleted = {}", note.getIsDeleted());
    }

    @Override
    public Collection<Note> readByStatus(String username, NoteStatus status) {
        log.info("readByStatus() - start: username = {}, status = {}", username, status);
        var author = authorServiceBean.getAuthorByUsername(username);
        Collection<Note> notes = new ArrayList<>();
        for (Note note : author.getNotes()) {
            if (note.getStatus().equals(status)) {
                notes.add(note);
            }
        }
        log.info("readByStatus() - end: notes count = {}", notes.size());

        return notes;
    }

    @Override
    public Collection<Note> readByType(String username, NoteType type) {
        log.info("readByType() - start: username = {}, type = {}", username, type);
        var author = authorServiceBean.getAuthorByUsername(username);
        Collection<Note> notes = new ArrayList<>();
        for (Note note : author.getNotes()) {
            if (note.getType().equals(type)) {
                notes.add(note);
            }
        }
        log.info("readByType() - end: notes count = {}", notes.size());

        return notes;
    }

    @Override
    public Collection<Note> readAllExpired(String username) {
        log.info("readAllExpired() - start: username = {}", username);
        var author = authorServiceBean.getAuthorByUsername(username);
        Collection<Note> notes = new ArrayList<>();
        for (Note note : author.getNotes()) {
            if (isNotNull(note.getEndTime()) && note.getEndTime().before(Date.from(Instant.now())) && note.getIsDone().equals(Boolean.FALSE)) {
                notes.add(note);
            }
        }
        log.info("readAllExpired() - end: notes count = {}", notes.size());
        return notes;
    }

    @Override
    public Collection<Note> readAllNotExpired(String username) {
        log.info("readAllNotExpired() - start: username = {}", username);
        var author = authorServiceBean.getAuthorByUsername(username);
        Collection<Note> notes = new ArrayList<>();
        for (Note note : author.getNotes()) {
            if (isNull(note.getEndTime()) || note.getEndTime().after(Date.from(Instant.now())) && note.getIsDone().equals(Boolean.FALSE)) {
                notes.add(note);
            }
        }
        log.info("readAllNotExpired() - end: notes count = {}", notes.size());
        return notes;
    }

    @Override
    public Note updateSetIsDone(String username, Integer id) {
        log.info("updateSetIsDone() - start: username = {}, id = {}", username, id);
        var note = getNoteByIdAndAuthorUsername(username, id);
        note.setIsDone(Boolean.TRUE);
        log.info("updateSetIsDone() - end: isDone = {}", note.getIsDone());
        return noteRepository.save(note);
    }

    @Override
    public Note updateSetNotDone(String username, Integer id) {
        log.info("updateSetNotDone() - start: username = {}, id = {}", username, id);
        var note = getNoteByIdAndAuthorUsername(username, id);
        note.setIsDone(Boolean.FALSE);
        log.info("updateSetNotDone() - end: isDone = {}", note.getIsDone());
        return noteRepository.save(note);
    }

    @Override
    public Collection<Note> readAllIsDone(String username) {
        log.info("readAllIsDone() - start: username = {}", username);
        var author = authorServiceBean.getAuthorByUsername(username);
        Collection<Note> notes = new ArrayList<>();
        for (Note note : author.getNotes()) {
            if (note.getIsDone().equals(Boolean.TRUE)) {
                notes.add(note);
            }
        }
        log.info("readAllIsDone() - end: notes count = {}", notes.size());
        return notes;
    }

    @Override
    public Collection<Note> readAllNotDone(String username) {
        log.info("readAllNotDone() - start: username = {}", username);
        var author = authorServiceBean.getAuthorByUsername(username);
        Collection<Note> notes = new ArrayList<>();
        for (Note note : author.getNotes()) {
            if (note.getIsDone().equals(Boolean.FALSE)) {
                notes.add(note);
            }
        }
        log.info("readAllNotDone() - end: notes count = {}", notes.size());
        return notes;
    }

    ///---Technical---\\\

    private Note getNoteByIdAndAuthorUsername(String username, Integer id) {
        return noteRepository.findNoteByIdAndAuthorUsername(username, id)
                .orElseThrow(ResourceNotFoundException::new);
    }

    private void trimNote(Note note) {
        if (note.getTitle() != null) note.setTitle(note.getTitle().trim());
        if (note.getDescription() != null) note.setDescription(note.getDescription().trim());
    }

    private Boolean isNotNull(Object obj) {
        return obj != null;
    }

    private Boolean isNull(Object obj) {
        return obj == null;
    }

}
