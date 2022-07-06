package com.kotyk.notepad.service;

import com.kotyk.notepad.domain.Note;

import java.util.Collection;

public interface NoteService {

    Note create(String username, Note note);

    Note read(String username, Integer id);

    Collection<Note> readByTitle(String username, String title);

    Collection<Note> readAll();

    Note update(String username, Note note);

    void delete(String username, Integer id);
}
