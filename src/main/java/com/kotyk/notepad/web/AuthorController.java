package com.kotyk.notepad.web;

import com.kotyk.notepad.dto.author.AuthorDeleteDto;
import com.kotyk.notepad.dto.author.AuthorDto;
import com.kotyk.notepad.dto.author.AuthorReadDto;
import com.kotyk.notepad.service.AuthorService;
import com.kotyk.notepad.util.config.AuthorMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping(value = "/notepad", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @PostMapping("/authors")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthorDto addAuthor(@RequestBody @Valid AuthorDto authorDto) {
        var author = AuthorMapper.INSTANCE.dtoToAuthor(authorDto);
        var dto = AuthorMapper.INSTANCE.authorToDto(authorService.create(author));

        return dto;
    }

    @GetMapping("/authors/{username}")
    @ResponseStatus(HttpStatus.OK)
    public AuthorReadDto readAuthor(@PathVariable String username) {
        var author = authorService.read(username);
        var dto = AuthorMapper.INSTANCE.authorToReadDto(author);

        return dto;
    }

    @GetMapping("/authors")
    @ResponseStatus(HttpStatus.OK)
    public Collection<AuthorDto> readAuthors() {
        var authors = authorService.readAll();
        var dto = AuthorMapper.INSTANCE.authorsToDtos(authors);

        return dto;
    }

    @PatchMapping ("/authors/{username}")
    @ResponseStatus(HttpStatus.OK)
    public AuthorDto patchAuthor(@PathVariable String username, @RequestBody /*@Valid*/ AuthorDto authorDto) {
        //todo Make validation
        var author = AuthorMapper.INSTANCE.dtoToAuthor(authorDto);
        var dto = AuthorMapper.INSTANCE.authorToDto(authorService.update(username, author));

        return dto;
    }

    @DeleteMapping("/authors/{username}")
    @ResponseStatus(HttpStatus.GONE)
    public AuthorDeleteDto deleteAuthor(@PathVariable String username) {
        authorService.delete(username);
        return new AuthorDeleteDto(username);
    }

}
