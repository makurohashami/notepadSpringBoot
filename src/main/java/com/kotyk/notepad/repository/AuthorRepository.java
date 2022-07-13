package com.kotyk.notepad.repository;

import com.kotyk.notepad.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {

    Boolean existsByEmailAndIsDeletedFalse(String email);

    Boolean existsByUsernameAndIsDeletedFalse(String username);

    @Query("select a from Author a where a.username = :username and a.isDeleted = false ")
    Optional<Author> findByUsername(String username);

    @Query("select a from Author a where a.isDeleted = false order by a.id asc ")
    List<Author> findAll();

}
