package com.kotyk.notepad.repository;

import com.kotyk.notepad.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {

    @Query("select count(a.username) from Author a where a.username = :username and a.isDeleted = false")
    Integer isUsernameExists(String username);

    @Query("select count(a.email) from Author a where a.email = :email and a.isDeleted = false")
    Integer isEmailExists(String email);

}
