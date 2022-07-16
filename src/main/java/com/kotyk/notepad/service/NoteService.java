package com.kotyk.notepad.service;

import com.kotyk.notepad.domain.Author;
import com.kotyk.notepad.domain.Note;
import com.kotyk.notepad.domain.NoteStatus;
import com.kotyk.notepad.domain.NoteType;

import java.util.Collection;

public interface NoteService {

    Author create(String username, Note note);

    Note read(String username, Integer id);

    Collection<Note> readByTitle(String username, String title);

    Collection<Note> readAll(String username);

    Note update(String username, Integer id, Note note);

    void delete(String username, Integer id);

    Collection<Note> readByStatus(String username, NoteStatus status);

    Collection<Note> readByType(String username, NoteType type);

    Collection<Note> readAllExpired(String username);

    Collection<Note> readAllNotExpired(String username);

    Note updateSetIsDone(String username, Integer id);

    Note updateSetNotDone(String username, Integer id);

    Collection<Note> readAllIsDone(String username);

    Collection<Note> readAllNotDone(String username);


}
