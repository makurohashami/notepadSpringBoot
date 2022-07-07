package com.kotyk.notepad.web;

import com.kotyk.notepad.dto.author.AuthorReadDto;
import com.kotyk.notepad.dto.note.NoteDeleteDto;
import com.kotyk.notepad.dto.note.NoteDto;
import com.kotyk.notepad.dto.note.NoteReadDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.Collection;

@Tag(name = "Notes", description = "CRUDs for notes")
public interface NoteSwagger {

    @Operation(summary = "This is endpoint to add a new note", description = "Create request to add a new note", tags = {"Notes"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CREATED. The new note is successfully created and added to database"),
            @ApiResponse(responseCode = "400", description = "Invalid input or validation failed"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Note or author of note not found")})
    AuthorReadDto createNote(String username, NoteDto noteDto);

    @Operation(summary = "This is endpoint to view note of author username by note ID", description = "Create request to view note by ID", tags = {"Notes"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK. View note by ID"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Note not found")})
    NoteReadDto viewNote(String username, Integer id);

    @Operation(summary = "This is endpoint to view all notes by title of author by username", description = "Create request to view all notes by title", tags = {"Notes"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK. View all notes"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Author not found")})
    Collection<NoteReadDto> viewNotesByTitle(String username, String title);

    @Operation(summary = "This is endpoint to view all notes of author by username", description = "Create request to view all notes", tags = {"Notes"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK. View all notes"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Author not found")})
    Collection<NoteDto> viewNotes(String username);

    @Operation(summary = "This is endpoint to change note by ID", description = "Create request to change note by ID", tags = "Notes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK. This note is successfully updated in database"),
            @ApiResponse(responseCode = "400", description = "Invalid input or validation failed"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Note or author not found")})
    NoteDto patchNote(String username, Integer id, NoteDto noteDto);

    @Operation(summary = "This is endpoint to delete note by ID", description = "Create request to delete note by ID. Make isDeleted = true", tags = {"Notes"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "410", description = "GONE. Note deleted successfully"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND.  Note or author not found")})
    NoteDeleteDto deleteNote(String username, Integer id);

}
