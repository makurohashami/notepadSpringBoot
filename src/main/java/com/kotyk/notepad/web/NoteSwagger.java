package com.kotyk.notepad.web;

import com.kotyk.notepad.domain.NoteStatus;
import com.kotyk.notepad.domain.NoteType;
import com.kotyk.notepad.dto.author.AuthorReadDto;
import com.kotyk.notepad.dto.note.NoteDeleteDto;
import com.kotyk.notepad.dto.note.NoteDto;
import com.kotyk.notepad.dto.note.NoteReadDto;
import com.kotyk.notepad.util.exception.Error;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.Collection;

@Tag(name = "Notes", description = "CRUD for notes")
public interface NoteSwagger {

    @Operation(summary = "This is endpoint to add a new note", description = "Create request to add a new note", tags = {"Notes"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CREATED. The new note is successfully created and added to database", content = @Content(schema = @Schema(implementation = AuthorReadDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input or validation failed", content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Note or author of note not found", content = @Content(schema = @Schema(implementation = Error.class)))})
    AuthorReadDto createNote(String username, NoteDto noteDto);

    @Operation(summary = "This is endpoint to view note of author username by note ID", description = "Create request to view note by ID", tags = {"Notes"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK. View note by ID"),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Note not found", content = @Content(schema = @Schema(implementation = Error.class)))})
    NoteReadDto viewNote(String username, Integer id);

    @Operation(summary = "This is endpoint to view all notes by title of author by username", description = "Create request to view all notes by title", tags = {"Notes"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK. View all notes"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Author not found", content = @Content(schema = @Schema(implementation = Error.class)))})
    Collection<NoteReadDto> viewNotesByTitle(String username, String title);

    @Operation(summary = "This is endpoint to view all notes of author by username", description = "Create request to view all notes", tags = {"Notes"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK. View all notes"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Author not found", content = @Content(schema = @Schema(implementation = Error.class)))})
    Collection<NoteDto> viewNotes(String username);

    @Operation(summary = "This is endpoint to change note by ID", description = "Create request to change note by ID", tags = "Notes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK. This note is successfully updated in database"),
            @ApiResponse(responseCode = "400", description = "Invalid input or validation failed", content = @Content(schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Note or author not found", content = @Content(schema = @Schema(implementation = Error.class)))})
    NoteDto patchNote(String username, Integer id, NoteDto noteDto);

    @Operation(summary = "This is endpoint to delete note by ID", description = "Create request to delete note by ID. Make isDeleted = true", tags = {"Notes"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "410", description = "GONE. Note deleted successfully"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND.  Note or author not found", content = @Content(schema = @Schema(implementation = Error.class)))})
    NoteDeleteDto deleteNote(String username, Integer id);

    @Operation(summary = "This is endpoint to view all notes by status of author by username", description = "Create request to view all notes by status", tags = {"Notes"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK. View notes"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Author not found", content = @Content(schema = @Schema(implementation = Error.class)))})
    Collection<NoteReadDto> viewNotesByStatus(String username, NoteStatus status);

    @Operation(summary = "This is endpoint to view all notes by type of author by username", description = "Create request to view all notes by type", tags = {"Notes"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK. View notes"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Author not found", content = @Content(schema = @Schema(implementation = Error.class)))})
    Collection<NoteReadDto> viewNotesByType(String username, NoteType type);

    @Operation(summary = "This is endpoint to view all expired notes of author by username", description = "Create request to view all expired notes", tags = {"Notes"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK. View notes"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Author not found", content = @Content(schema = @Schema(implementation = Error.class)))})
    Collection<NoteReadDto> viewExpiredNotes(String username);

    @Operation(summary = "This is endpoint to view all not expired notes of author by username", description = "Create request to view all not expired notes", tags = {"Notes"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK. View notes"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Author not found", content = @Content(schema = @Schema(implementation = Error.class)))})
    Collection<NoteReadDto> viewNotExpiredNotes(String username);

    @Operation(summary = "This is endpoint to set true for isDone note", description = "Create request to set isDome = true", tags = {"Notes"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK. View note"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Author not found", content = @Content(schema = @Schema(implementation = Error.class)))})
    NoteReadDto setIsDone(String username, Integer id);

    @Operation(summary = "This is endpoint to set false for isDone note", description = "Create request to set isDome = false", tags = {"Notes"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK. View note"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Author not found", content = @Content(schema = @Schema(implementation = Error.class)))})
    NoteReadDto setNotDone(String username, Integer id);

    @Operation(summary = "This is endpoint to view all done notes of author by username", description = "Create request to view all done notes", tags = {"Notes"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK. View notes"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Author not found", content = @Content(schema = @Schema(implementation = Error.class)))})
    Collection<NoteReadDto> viewAllDone(String username);

    @Operation(summary = "This is endpoint to view all not done notes of author by username", description = "Create request to view all not done notes", tags = {"Notes"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK. View notes"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Author not found", content = @Content(schema = @Schema(implementation = Error.class)))})
    Collection<NoteReadDto> viewAllNotDone(String username);

}
