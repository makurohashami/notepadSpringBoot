package com.kotyk.notepad.service;

import com.kotyk.notepad.domain.Author;
import com.kotyk.notepad.domain.Note;
import com.kotyk.notepad.repository.AuthorRepository;
import com.kotyk.notepad.util.exception.EmailAlreadyExistsException;
import com.kotyk.notepad.util.exception.ResourceNotFoundException;
import com.kotyk.notepad.util.exception.UsernameAlreadyExistsException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
         Author author = getAuthorByUsername(username);
         Collection<Note> notes = new ArrayList<>();
         for(Note note : author.getNotes()) {
             if(note.getIsDeleted().equals(Boolean.FALSE)) {
                 notes.add(note);
             }
         }
         author.setNotes(notes);

         return author;
    }

    @Override
    public Collection<Author> readAll() {
        return authorRepository.findAll();
    }

    @Override
    public Author update(String username, Author author) {

        Author newAuthor = getAuthorByUsername(username);

        if(author.getUsername() != null) {
            if(authorRepository.isUsernameExists(author.getUsername()) > 0) {
                throw new UsernameAlreadyExistsException();
            } else {
                newAuthor.setUsername(author.getUsername());
            }
        }

        if(author.getEmail() != null) {
            if(authorRepository.isEmailExists(author.getEmail()) > 0) {
                throw new EmailAlreadyExistsException();
            } else {
                newAuthor.setEmail(author.getEmail());
            }
        }

        if(author.getName() != null) {
            newAuthor.setName(author.getName());
        }

        return authorRepository.save(newAuthor);
    }

    @Override
    public void delete(String username) {
        Author author = getAuthorByUsername(username);
        author.setIsDeleted(Boolean.TRUE);
        for(Note notes: author.getNotes()) {
            notes.setIsDeleted(Boolean.TRUE);
        }
        authorRepository.save(author);
    }

    ///---Technical---\\\

    private Author getAuthorByUsername(String username) {
        return authorRepository.findByUsername(username)
                .orElseThrow(ResourceNotFoundException::new);
    }
}
