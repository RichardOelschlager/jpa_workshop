package com.example.jpa_workshop.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    private LocalDate registrationDate;

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "details_id", referencedColumnName = "id")
    private Details details;

    @OneToMany(mappedBy = "borrower", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Builder.Default
    private List<BookLoan> bookLoans = new ArrayList<>();

    public void addBookLoan(BookLoan loan) {
        bookLoans.add(loan);
        loan.setBorrower(this);
    }
}