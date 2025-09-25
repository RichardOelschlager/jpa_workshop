package com.example.jpa_workshop.repo;

import com.example.jpa_workshop.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Integer> {

    List<Author> findByFirstName(String firstName);

    List<Author> findByLastName(String lastName);

    List<Author> findByFirstNameContainingOrLastNameContaining(String first, String last);

    // Find by book ID
    List<Author> findByBooks_Id(int bookId);

    // Update name by ID
    @Modifying
    @Query("UPDATE Author a SET a.firstName = :firstName, a.lastName = :lastName WHERE a.authorId = :id")
    int updateNameById(int id, String firstName, String lastName);

    // Delete by ID → already included in JpaRepository (deleteById)
}
