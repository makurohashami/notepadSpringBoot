package com.kotyk.notepad.web;

import com.kotyk.notepad.dto.author.AuthorDeleteDto;
import com.kotyk.notepad.dto.author.AuthorDto;
import com.kotyk.notepad.dto.author.AuthorReadDto;
import com.kotyk.notepad.util.exception.Error;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.Collection;

@Tag(name = "Authors", description = "CRUD for authors")
public interface AuthorSwagger {

    @Operation(summary = "This is endpoint to add a new author", description = "Create request to add a new author", tags = {"Authors"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CREATED. The new author is successfully created and added to database", content = @Content(schema = @Schema(implementation = AuthorDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input or validation failed", content = @Content(schema = @Schema(implementation = Error.class)))})
    AuthorDto addAuthor(AuthorDto authorDto);

    @Operation(summary = "This is endpoint to view author by username", description = "Create request to view author", tags = {"Authors"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK. View author by username", content = @Content(schema = @Schema(implementation = AuthorReadDto.class))),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Author not found", content = @Content(schema = @Schema(implementation = Error.class)))})
    AuthorReadDto readAuthor(String username);

    @Operation(summary = "This is endpoint to view all authors", description = "Create request to view all authors", tags = {"Authors"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK. View all authors"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Author not found", content = @Content(schema = @Schema(implementation = Error.class)))})
    Collection<AuthorDto> readAuthors();

    @Operation(summary = "This is endpoint to change author by username", description = "Create request to change author by username", tags = "Authors")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK. This note is successfully updated in database", content = @Content(schema = @Schema(implementation = AuthorDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input or validation failed", content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Author not found", content = @Content(schema = @Schema(implementation = Error.class)))})
    AuthorDto patchAuthor(String username, AuthorDto authorDto);

    @Operation(summary = "This is endpoint to delete author by username", description = "Create request to delete author by username. Make isDeleted = true for author and for all authors notes", tags = {"Authors"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "410", description = "GONE. Author deleted successfully", content = @Content(schema = @Schema(implementation = AuthorDeleteDto.class))),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Author not found", content = @Content(schema = @Schema(implementation = Error.class)))})
    AuthorDeleteDto deleteAuthor(String username);
}
