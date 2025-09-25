package com.example.jpa_workshop;

import com.example.jpa_workshop.entity.Author;
import com.example.jpa_workshop.entity.Book;
import com.example.jpa_workshop.repo.AuthorRepository;
import com.example.jpa_workshop.repo.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class AuthorRepositoryTests {

    @Autowired
    AuthorRepository authorRepo;
    @Autowired
    BookRepository bookRepo;

    @Test
    @Transactional
    void testAuthorQueries() {
        Book book = bookRepo.save(Book.builder()
                .isbn("111-222-333")
                .title("JPA Mastery")
                .maxLoanDays(10)
                .build());

        Author a1 = Author.builder().firstName("John").lastName("Doe").build();
        Author a2 = Author.builder().firstName("Jane").lastName("Smith").build();

        a1.getBooks().add(book);
        a2.getBooks().add(book);
        book.getAuthors().addAll(List.of(a1, a2));

        authorRepo.saveAll(List.of(a1, a2));

        assertThat(authorRepo.findByFirstName("John")).hasSize(1);
        assertThat(authorRepo.findByLastName("Smith")).hasSize(1);
        assertThat(authorRepo.findByFirstNameContainingOrLastNameContaining("Jane", "nothere")).hasSize(1);
        assertThat(authorRepo.findByBooks_Id(book.getId())).hasSize(2);

        // Update name
        authorRepo.updateNameById(a1.getAuthorId(), "Johnny", "Updated");
        assertThat(authorRepo.findByFirstName("Johnny")).isNotEmpty();
    }
}
