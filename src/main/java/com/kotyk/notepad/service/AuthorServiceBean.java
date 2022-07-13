package com.kotyk.notepad.service;

import com.kotyk.notepad.domain.Author;
import com.kotyk.notepad.repository.AuthorRepository;
import com.kotyk.notepad.repository.NoteRepository;
import com.kotyk.notepad.util.exception.EmailAlreadyExistsException;
import com.kotyk.notepad.util.exception.ResourceNotFoundException;
import com.kotyk.notepad.util.exception.UsernameAlreadyExistsException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Log4j2
@AllArgsConstructor
public class AuthorServiceBean implements AuthorService {

    private final AuthorRepository authorRepository;

    private final NoteRepository noteRepository;

    @Override
    public Author create(Author author) {
        log.info("create() - start: author = {}", author);
        trimAuthor(author);
        if (isUsernameNotTaken(author.getUsername()) && isEmailNotTaken(author.getEmail())) {
            var saved = authorRepository.save(author);
            log.info("create() - end: id = {}", saved.getId());
            return saved;
        }
        return null;
    }

    @Override
    public Author read(String username) {
        log.info("read() - start: username = {}", username);
        var author = getAuthorByUsername(username);
        log.info("read() - end: author = {}", author);
        return author;
    }

    @Override
    public Collection<Author> readAll() {
        log.info("readAll() - start:");
        var authors = authorRepository.findAll();
        log.info("readAll() - end: authors count = {}", authors.size());
        return authors;
    }

    @Override
    public Author update(String username, Author author) {
        log.info("update() - start: username = {}, author = {}", username, author);
        var newAuthor = getAuthorByUsername(username);
        trimAuthor(author);
        if (author.getUsername() != null && author.getUsername().length() > 0 && isUsernameNotTaken(author.getUsername())) {
            newAuthor.setUsername(author.getUsername());
        }
        if (author.getName() != null && author.getName().length() > 0) {
            newAuthor.setName(author.getName());
        }
        if (author.getEmail() != null && isEmailNotTaken(author.getEmail())) {
            Pattern pattern = Pattern.compile("^[A-Za-z\\d+_.-]+@(.+)");
            Matcher matcher = pattern.matcher(author.getEmail());
            if (matcher.matches()) newAuthor.setEmail(author.getEmail());
        }
        log.info("update() - end: new author = {}", newAuthor);
        return authorRepository.save(newAuthor);
    }

    @Override
    public void delete(String username) {
        //todo fix exception
        log.info("delete() - start: username = {}", username);
        var author = getAuthorByUsername(username);
        author.setIsDeleted(Boolean.TRUE);
        noteRepository.deleteNotesByAuthorUsername(username);
        //noteRepository.deleteNotesByAuthorId(author.getId());
        log.info("delete() - end: isDeleted = {}", author.getIsDeleted());
        authorRepository.save(author);
    }

    ///---Technical---\\\

    private Author getAuthorByUsername(String username) {
        var author = authorRepository.findByUsername(username)
                .orElseThrow(ResourceNotFoundException::new);
        author.setNotes(noteRepository.findAll(username));
        return author;
    }

    private Boolean isUsernameNotTaken(String username) {
        if (authorRepository.isUsernameExists(username) > 0) {
            throw new UsernameAlreadyExistsException();
        }
        return Boolean.TRUE;
    }

    private Boolean isEmailNotTaken(String email) {
        if (authorRepository.isEmailExists(email) > 0) {
            throw new EmailAlreadyExistsException();
        }
        return Boolean.TRUE;
    }

    private void trimAuthor(Author author) {
        if (author.getName() != null) author.setName(author.getName().trim());
        if (author.getEmail() != null) author.setEmail(author.getEmail().trim());
        if (author.getUsername() != null) author.setUsername(author.getUsername().trim());
    }
}
