package com.kotyk.notepad.service;

import com.kotyk.notepad.domain.Author;
import com.kotyk.notepad.repository.AuthorRepository;
import com.kotyk.notepad.util.exception.EmailAlreadyExistsException;
import com.kotyk.notepad.util.exception.ResourceNotFoundException;
import com.kotyk.notepad.util.exception.UsernameAlreadyExistsException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@AllArgsConstructor
public class AuthorServiceBean implements AuthorService{

    private final AuthorRepository authorRepository;

    @Override
    public Author create(Author author) {
        if(authorRepository.isUsernameExists(author.getUsername()) > 0) {
            throw new UsernameAlreadyExistsException();
        } else if (authorRepository.isEmailExists(author.getEmail()) > 0) {
            throw new EmailAlreadyExistsException();
        } else {
            return authorRepository.save(author);
        }
    }

    @Override
    public Author read(String username) {
         return getAuthorByUsername(username);
    }

    @Override
    public Collection<Author> readAll() {
        return authorRepository.findAll();
    }

    @Override
    public Author update(String username, Author author) {
        return authorRepository.findByUsername(username)
                .map(entity -> {
                    entity.setUsername(author.getUsername());
                    entity.setName(author.getName());
                    entity.setEmail(author.getEmail());
                    entity.setNotes(author.getNotes());

                    return authorRepository.save(entity);
                })
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void delete(String username) {
        Author author = getAuthorByUsername(username);
        author.setIsDeleted(Boolean.TRUE);
        authorRepository.save(author);
    }

    ///---Technical---\\\

    private Author getAuthorByUsername(String username) {
        Author author = authorRepository.findByUsername(username)
                .orElseThrow(ResourceNotFoundException::new);
        return author;
    }
}
