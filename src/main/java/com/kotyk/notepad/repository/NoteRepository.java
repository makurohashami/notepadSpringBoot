package com.kotyk.notepad.repository;

import com.kotyk.notepad.domain.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoteRepository extends JpaRepository<Note, Integer> {

    @Query("select n from Note n where n.id = :id and n.noteAuthor.username = :username and n.isDeleted = false and n.noteAuthor.isDeleted = false")
    Optional<Note> findNoteByIdAndAuthorUsername(String username, Integer id);

    @Query("select n from Note n where n.noteAuthor.username = :username and n.title = :title and n.isDeleted = false and n.noteAuthor.isDeleted = false order by n.timeOfCreation asc")
    List<Note> findByTitle(String username, String title);

    @Query("select n from Note n where n.noteAuthor.username = :username and n.isDeleted = false and n.noteAuthor.isDeleted = false order by n.timeOfCreation asc")
    List<Note> findAll(String username);

    @Modifying
    @Transactional
    @Query("update Note n set n.isDeleted = true where n.noteAuthor.username = :username")
    void deleteNotesByAuthorUsername(String username);

    @Modifying
    @Transactional
    @Query("update Note n set n.isDeleted = true where n.noteAuthor.id = :id")
    void deleteNotesByAuthorId(Integer id);

}
