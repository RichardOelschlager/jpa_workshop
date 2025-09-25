package com.example.jpa_workshop;

import com.example.jpa_workshop.entity.AppUser;
import com.example.jpa_workshop.entity.Book;
import com.example.jpa_workshop.entity.BookLoan;
import com.example.jpa_workshop.entity.Details;
import com.example.jpa_workshop.repo.AppUserRepository;
import com.example.jpa_workshop.repo.BookLoanRepository;
import com.example.jpa_workshop.repo.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class BookLoanRepositoryTests {

    @Autowired
    BookLoanRepository loanRepo;
    @Autowired
    BookRepository bookRepo;
    @Autowired
    AppUserRepository userRepo;

    @Test
    @Transactional
    void testLoanLifecycle() {
        AppUser user = userRepo.save(AppUser.builder()
                .username("reader1")
                .registrationDate(LocalDate.now())
                .details(Details.builder().name("Reader One").email("reader@x.com").build())
                .build());

        Book book = bookRepo.save(Book.builder()
                .isbn("123-456-789")
                .title("Spring in Action")
                .maxLoanDays(14)
                .build());

        BookLoan loan = loanRepo.save(BookLoan.builder()
                .borrower(user)
                .book(book)
                .loanDate(LocalDate.now())
                .dueDate(LocalDate.now().plusDays(14))
                .build());

        assertThat(loanRepo.findByBorrower_Id(user.getId())).hasSize(1);
        assertThat(loanRepo.findByBook_Id(book.getId())).hasSize(1);
        assertThat(loanRepo.findByReturnedDateIsNull()).hasSize(1);

        // Mark as returned
        loanRepo.markAsReturned(loan.getId());
        assertThat(loanRepo.findByReturnedDateIsNull()).isEmpty();
    }
}