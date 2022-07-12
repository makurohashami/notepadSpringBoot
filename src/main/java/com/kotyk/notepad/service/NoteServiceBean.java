package com.kotyk.notepad.service;

import com.kotyk.notepad.domain.Author;
import com.kotyk.notepad.domain.Note;
import com.kotyk.notepad.repository.AuthorRepository;
import com.kotyk.notepad.repository.NoteRepository;
import com.kotyk.notepad.util.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

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
        //todo fix author output
        log.info("create() - end: author = {}", author);
        return authorRepository.save(author);
    }

    @Override
    public Note read(String username, Integer id) {
        log.info("read() - start: username = {}, id = {}", username, id);
        var note = getNoteByIdAndAuthorUsername(username, id);
        //todo fix note output
        log.info("read() - end: note = {}", note);

        return note;
    }

    @Override
    public Collection<Note> readByTitle(String username, String title) {
        log.info("readByTitle() - start: username = {}, title = {}", username, title);
        var notes = noteRepository.findByTitle(username, title);
        //todo fix notes output
        log.info("readByTitle() - end: notes count = {}", notes.size());

        return notes;
    }

    @Override
    public Collection<Note> readAll(String username) {
        log.info("readAll() - start: username = {}", username);
        var notes = noteRepository.findAll(username);
        //todo fix notes output
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

        //todo fix newNote output
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

    ///---Technical---\\\

    private Author getAuthorByUsername(String username) {
        return authorRepository.findByUsername(username)
                .orElseThrow(ResourceNotFoundException::new);
    }

    private Note getNoteByIdAndAuthorUsername(String username, Integer id) {
        return noteRepository.findByIdAndAuthorUsername(username, id)
                .orElseThrow(ResourceNotFoundException::new);
    }

}
