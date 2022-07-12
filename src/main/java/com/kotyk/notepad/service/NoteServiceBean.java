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

import java.util.ArrayList;
import java.util.Collection;

@Service
@AllArgsConstructor
@Log4j2
public class NoteServiceBean implements NoteService {

    private final AuthorRepository authorRepository;
    private final NoteRepository noteRepository;

    @Override
    public Author create(String username, Note note) {
        log.info("create() - start: username = {}, note = {}", username, note);
        note.setTitle(note.getTitle().trim());
        Author author = getAuthorByUsername(username.trim());
        note.setNoteAuthor(author);
        author.getNotes().add(note);
        log.info("create() - end: author = {}", author);
        return authorRepository.save(author);
    }

    @Override
    public Note read(String username, Integer id) {
        log.info("read() - start: username = {}, id = {}", username, id);
        var note = getNoteByIdAndAuthorUsername(username, id);
        log.info("read() - end: note = {}", note);

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
        log.info("update() - start: username = {}, id = {}, note = {}", username, id, note);
        Note newNote = getNoteByIdAndAuthorUsername(username, id);

        if (note.getTitle() != null && note.getTitle().trim().length() > 0) {
            newNote.setTitle(note.getTitle().trim());
        }
        if (note.getDescription() != null && note.getDescription().trim().length() > 0) {
            newNote.setDescription(note.getDescription().trim());
        }
        if (note.getType() != null) {
            newNote.setType(note.getType());
        }
        if (note.getStatus() != null) {
            newNote.setStatus(note.getStatus());
        }

        log.info("update() - end: note = {}", newNote);
        return noteRepository.save(newNote);
    }

    @Override
    public void delete(String username, Integer id) {
        log.info("delete() - start: username = {}, id = {}", username, id);
        Note note = getNoteByIdAndAuthorUsername(username, id);
        note.setIsDeleted(Boolean.TRUE);
        noteRepository.save(note);
        log.info("delete() - end: isDeleted = {}", note.getIsDeleted());
    }

    @Override
    public Collection<Note> readByStatus(String username, NoteStatus status) {
        log.info("readByStatus() - start: username = {}, status = {}", username, status);
        var author = getAuthorByUsername(username);
        Collection<Note> notes = new ArrayList<>();
        for(Note note: author.getNotes()) {
            if(note.getStatus().equals(status) && note.getIsDeleted().equals(Boolean.FALSE)) {
                notes.add(note);
            }
        }
        log.info("readByStatus() - end: notes = {}", notes);

        return notes;
    }

    @Override
    public Collection<Note> readByType(String username, NoteType type) {
        log.info("readByType() - start: username = {}, type = {}", username, type);
        var author = getAuthorByUsername(username);
        Collection<Note> notes = new ArrayList<>();
        for(Note note: author.getNotes()) {
            if(note.getType().equals(type) && note.getIsDeleted().equals(Boolean.FALSE)) {
                notes.add(note);
            }
        }
        log.info("readByType() - end: notes = {}", notes);

        return notes;
    }

    ///---Technical---\\\

    private Author getAuthorByUsername(String username) {
        return authorRepository.findByUsername(username)
                .orElseThrow(ResourceNotFoundException::new);
    }

    private Note getNoteByIdAndAuthorUsername(String username, Integer id) {
        return noteRepository.findNoteByIdAndAuthorUsername(username, id)
                .orElseThrow(ResourceNotFoundException::new);
    }

}
