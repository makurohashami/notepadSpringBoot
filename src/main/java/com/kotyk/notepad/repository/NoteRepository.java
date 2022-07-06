package com.kotyk.notepad.repository;

import com.kotyk.notepad.domain.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Integer> {

    @Query("select n from Note n where n.noteAuthor.id = :id and n.isDeleted = false and n.noteAuthor.isDeleted = false ")
    List<Note> findAll(Integer id);
}
