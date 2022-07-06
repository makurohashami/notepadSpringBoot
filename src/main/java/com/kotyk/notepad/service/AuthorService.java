package com.kotyk.notepad.service;

import com.kotyk.notepad.domain.Author;

import java.util.Collection;

public interface AuthorService {

    Author create(Author author);

    Author read(String username);

    Collection<Author> readAll();

    Author update(Author author);

    void delete(String username);

}
