package com.kotyk.notepad.service;

import com.kotyk.notepad.domain.Author;
import com.kotyk.notepad.domain.Note;
import com.kotyk.notepad.domain.NoteStatus;

import java.util.Collection;

public interface NoteService {

    Author create(String username, Note note);

    Note read(String username, Integer id);

    Collection<Note> readByTitle(String username, String title);

    Collection<Note> readAll(String username);

    Note update(String username, Integer id, Note note);

    void delete(String username, Integer id);

    Collection<Note> readByStatus(String username, NoteStatus status);

}
