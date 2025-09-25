package com.example.jpa_workshop.repo;


import com.example.jpa_workshop.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Integer> {

    Optional<Book> findByIsbnIgnoreCase(String isbn);

    List<Book> findByTitleContaining(String keyword);

    List<Book> findByMaxLoanDaysLessThan(int days);
}

