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
}