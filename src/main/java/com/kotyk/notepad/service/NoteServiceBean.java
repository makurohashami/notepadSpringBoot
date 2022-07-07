package com.kotyk.notepad.service;

import com.kotyk.notepad.domain.Author;
import com.kotyk.notepad.domain.Note;
import com.kotyk.notepad.repository.AuthorRepository;
import com.kotyk.notepad.repository.NoteRepository;
import com.kotyk.notepad.util.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@AllArgsConstructor
public class NoteServiceBean implements NoteService {

    private final AuthorRepository authorRepository;
    private final NoteRepository noteRepository;

    @Override
    public Author create(String username, Note note) {
        Author author = getAuthorByUsername(username);
        note.setNoteAuthor(author);
        author.getNotes().add(note);

       return authorRepository.save(author);
    }

    @Override
    public Note read(String username, Integer id) {
        return getNoteByIdAndAuthorUsername(username, id);
    }

    @Override
    public Collection<Note> readByTitle(String username, String title) {
        return noteRepository.findByTitle(username, title);
    }

    @Override
    public Collection<Note> readAll(String username) {
        return noteRepository.findAll(username);
    }

    //todo
    @Override
    public Note update(String username, Integer id, Note note) {
        return null;
    }

    @Override
    public void delete(String username, Integer id) {
        Note note = getNoteByIdAndAuthorUsername(username, id);
        note.setIsDeleted(Boolean.TRUE);
        noteRepository.save(note);
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
