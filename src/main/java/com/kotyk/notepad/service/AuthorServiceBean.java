package com.kotyk.notepad.service;

import com.kotyk.notepad.domain.Author;
import com.kotyk.notepad.domain.Note;
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
        log.info("read() - end: author's username = {}", author.getUsername());
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
        if (isNotNull(author.getUsername()) && !author.getUsername().isBlank() && isUsernameNotTaken(author.getUsername())) {
            newAuthor.setUsername(author.getUsername());
        }
        if (isNotNull(author.getName()) && !author.getName().isBlank()) {
            newAuthor.setName(author.getName());
        }
        if (isNotNull(author.getEmail()) && isEmailNotTaken(author.getEmail())) {
            Pattern pattern = Pattern.compile("^[A-Za-z\\d+_.-]+@(.+)");
            Matcher matcher = pattern.matcher(author.getEmail());
            if (matcher.matches()) newAuthor.setEmail(author.getEmail());
        }
        log.info("update() - end: author's username = {}", author.getUsername());
        return authorRepository.save(newAuthor);
    }

    @Override
    public void delete(String username) {
        log.info("delete() - start: username = {}", username);
        var author = getAuthorByUsername(username);
        author.setIsDeleted(Boolean.TRUE);
        for (Note note : author.getNotes()) {
            note.setIsDeleted(Boolean.TRUE);
        }
        log.info("delete() - end: isDeleted = {}", author.getIsDeleted());
        authorRepository.save(author);
    }

    ///---Technical---\\\

    protected Author getAuthorByUsername(String username) {
        var author = authorRepository.findByUsername(username)
                .orElseThrow(ResourceNotFoundException::new);
        author.setNotes(noteRepository.findAll(username));
        return author;
    }

    protected Boolean isNotNull(Object obj) {
        return obj != null;
    }

    private Boolean isUsernameNotTaken(String username) {
        if (authorRepository.existsByUsernameAndIsDeletedFalse(username)) {
            throw new UsernameAlreadyExistsException();
        }
        return Boolean.TRUE;
    }

    private Boolean isEmailNotTaken(String email) {
        if (authorRepository.existsByEmailAndIsDeletedFalse(email)) {
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
