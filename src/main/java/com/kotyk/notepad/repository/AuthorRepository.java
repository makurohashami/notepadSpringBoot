package com.kotyk.notepad.repository;

import com.kotyk.notepad.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {

    @Query("select count(a.username) from Author a where a.username = :username and a.isDeleted = false")
    Integer isUsernameExists(String username);

    @Query("select count(a.email) from Author a where a.email = :email and a.isDeleted = false")
    Integer isEmailExists(String email);

    @Query("select a from Author a where a.username = :username and a.isDeleted = false ")
    Optional<Author> findByUsername(String username);

    @Query("select a from Author a where a.isDeleted = false ")
    List<Author> findAll();
}
