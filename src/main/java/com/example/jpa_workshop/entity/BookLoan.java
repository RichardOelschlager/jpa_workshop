package com.example.jpa_workshop.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookLoan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDate loanDate;
    private LocalDate dueDate;
    private LocalDate returnedDate;  // null = not yet returned

    @ManyToOne(optional = false)
    @JoinColumn(name = "borrower_id", referencedColumnName = "id")
    private AppUser borrower;

    @ManyToOne(optional = false)
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private Book book;

    public static BookLoan createLoan(Book book, AppUser user) {
        if (!book.isAvailable()) {
            throw new IllegalStateException("Book is not available for loan.");
        }

        LocalDate now = LocalDate.now();

        BookLoan loan = BookLoan.builder()
                .book(book)
                .borrower(user)
                .loanDate(now)
                .dueDate(now.plusDays(book.getMaxLoanDays()))
                .build();

        // link both sides
        user.addBookLoan(loan);
        book.setAvailable(false);

        return loan;
    }

}