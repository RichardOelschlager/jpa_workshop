package com.example.jpa_workshop.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

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
}