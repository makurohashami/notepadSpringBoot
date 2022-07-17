package com.kotyk.notepad.web;

import com.kotyk.notepad.dto.author.AuthorDeleteDto;
import com.kotyk.notepad.dto.author.AuthorDto;
import com.kotyk.notepad.dto.author.AuthorReadDto;
import com.kotyk.notepad.service.AuthorService;
import com.kotyk.notepad.util.config.AuthorMapper;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping(value = "/notepad", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Log4j2
public class AuthorController implements AuthorSwagger {

    private final AuthorService authorService;

    @PostMapping("/authors")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthorDto addAuthor(@RequestBody @Valid AuthorDto authorDto) {
        log.debug("Controller: addAuthor() - start: authorDto = {}", authorDto);
        var author = AuthorMapper.INSTANCE.dtoToAuthor(authorDto);
        var dto = AuthorMapper.INSTANCE.authorToDto(authorService.create(author));

        log.debug("Controller: addAuthor() - end: author = {}", dto);
        return dto;
    }

    @GetMapping("/authors/{username}")
    @ResponseStatus(HttpStatus.OK)
    public AuthorReadDto readAuthor(@PathVariable String username) {
        log.debug("readAuthor() Controller - start: username = {}", username);
        var author = authorService.read(username);
        var dto = AuthorMapper.INSTANCE.authorToReadDto(author);

        log.debug("readAuthor() Controller - end: author = {}", dto);
        return dto;
    }

    @GetMapping("/authors")
    @ResponseStatus(HttpStatus.OK)
    public Collection<AuthorDto> readAuthors() {
        log.debug("Controller: readAuthors() - start:");
        var authors = authorService.readAll();
        var dto = AuthorMapper.INSTANCE.authorsToDtos(authors);

        log.debug("Controller: readAuthors() - end: authors = {}", dto);
        return dto;
    }

    @PatchMapping("/authors/{username}")
    @ResponseStatus(HttpStatus.OK)
    public AuthorDto patchAuthor(@PathVariable String username, @RequestBody AuthorDto authorDto) {
        log.debug("Controller: patchAuthor() - start: username = {}, authorDto = {}", username, authorDto);
        var author = AuthorMapper.INSTANCE.dtoToAuthor(authorDto);
        var dto = AuthorMapper.INSTANCE.authorToDto(authorService.update(username, author));

        log.debug("Controller: patchAuthor() - end: author = {}", dto);
        return dto;
    }

    @DeleteMapping("/authors/{username}")
    @ResponseStatus(HttpStatus.GONE)
    public AuthorDeleteDto deleteAuthor(@PathVariable String username) {
        log.debug("Controller: deleteAuthor() - start: username = {}", username);
        authorService.delete(username);
        log.debug("Controller: deleteAuthor() - end: Author was deleted successfully");
        return new AuthorDeleteDto(username);
    }

}
