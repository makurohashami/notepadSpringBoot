package com.kotyk.notepad.util.config;

import com.kotyk.notepad.domain.Author;
import com.kotyk.notepad.dto.author.AuthorDto;
import com.kotyk.notepad.dto.author.AuthorReadDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Collection;

@Mapper
public interface AuthorMapper {

    AuthorMapper INSTANCE = Mappers.getMapper(AuthorMapper.class);

    Author dtoToAuthor(AuthorDto dto);

    AuthorDto authorToDto(Author author);

    AuthorReadDto authorToReadDto(Author author);

    Collection<AuthorDto> authorsToDtos(Collection<Author> authors);

}
