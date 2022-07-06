package com.kotyk.notepad.service;

import com.kotyk.notepad.domain.Author;
import com.kotyk.notepad.repository.AuthorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@AllArgsConstructor
public class AuthorServiceBean implements AuthorService{

    private final AuthorRepository authorRepository;

    @Override
    public Author create(Author author) {
        return null;
    }

    @Override
    public Author read(String username) {
        return null;
    }

    @Override
    public Collection<Author> readAll() {
        return null;
    }

    @Override
    public Author update(Author author) {
        return null;
    }

    @Override
    public void delete(String username) {

    }
}
