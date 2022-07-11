package com.kotyk.notepad.service;

import com.kotyk.notepad.domain.Author;
import com.kotyk.notepad.domain.Note;
import com.kotyk.notepad.repository.AuthorRepository;
import com.kotyk.notepad.util.exception.EmailAlreadyExistsException;
import com.kotyk.notepad.util.exception.ResourceNotFoundException;
import com.kotyk.notepad.util.exception.UsernameAlreadyExistsException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
@Log4j2
@AllArgsConstructor
public class AuthorServiceBean implements AuthorService{

    private final AuthorRepository authorRepository;

    @Override
    public Author create(Author author) {
        log.info("create() - start: author = {}", author);
        author.setName(author.getName().trim());
        author.setEmail(author.getEmail().trim());
        author.setUsername(author.getUsername().trim());

        if(authorRepository.isUsernameExists(author.getUsername()) > 0) {
            log.info("create() - exception: username taken, username = {}", author.getUsername());
            throw new UsernameAlreadyExistsException();
        } else if (authorRepository.isEmailExists(author.getEmail()) > 0) {
            log.info("create() - exception: email taken, email = {}", author.getEmail());
            throw new EmailAlreadyExistsException();
        } else {
            var saved = authorRepository.save(author);
            log.info("create() - end: id = {}", saved.getId());
            return saved;
        }
    }

    @Override
    public Author read(String username) {
        log.info("read() - start: username = {}", username);
         Author author = getAuthorByUsername(username);
         Collection<Note> notes = new ArrayList<>();
         for(Note note : author.getNotes()) {
             if(note.getIsDeleted().equals(Boolean.FALSE)) {
                 notes.add(note);
             }
         }
         author.setNotes(notes);
         //todo fix output author with notes
         log.info("read() - end: author = {}", author);
         return author;
    }

    @Override
    public Collection<Author> readAll() {
        log.info("readAll() - start:");
        Collection<Author> authors = authorRepository.findAll();
        log.info("readAll() - end: authors size = {}", authors.size());
        return authors;
    }

    @Override
    public Author update(String username, Author author) {
        log.info("update() - start: username = {}, author = {}", username, author);

        Author newAuthor = getAuthorByUsername(username);

        if(author.getUsername() != null && author.getUsername().trim().length() > 0) {
            author.setUsername(author.getUsername().trim());
            if(authorRepository.isUsernameExists(author.getUsername()) > 0) {
                log.info("create() - exception: username taken, username = {}", author.getUsername());
                throw new UsernameAlreadyExistsException();
            } else {
                newAuthor.setUsername(author.getUsername().trim());
            }
        }

        if(author.getEmail() != null) {
            //todo Email validation
            author.setEmail(author.getEmail().trim());
            if(authorRepository.isEmailExists(author.getEmail()) > 0) {
                log.info("create() - exception: email taken, email = {}", author.getEmail());
                throw new EmailAlreadyExistsException();
            } else {
                newAuthor.setEmail(author.getEmail());
            }
        }

        if(author.getName() != null && author.getName().trim().length() > 0) {
            newAuthor.setName(author.getName().trim());
        }

        log.info("update() - end: new author = {}", newAuthor);
        return authorRepository.save(newAuthor);
    }

    @Override
    public void delete(String username) {
        log.info("delete() - start: username = {}", username);
        Author author = getAuthorByUsername(username);
        author.setIsDeleted(Boolean.TRUE);
        for(Note notes: author.getNotes()) {
            notes.setIsDeleted(Boolean.TRUE);
        }
        log.info("delete() - end: isDeleted = {}", author.getIsDeleted());
        authorRepository.save(author);
    }

    ///---Technical---\\\

    private Author getAuthorByUsername(String username) {
        return authorRepository.findByUsername(username)
                .orElseThrow(ResourceNotFoundException::new);
    }
}
