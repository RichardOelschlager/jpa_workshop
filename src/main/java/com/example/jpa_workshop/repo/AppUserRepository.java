package com.example.jpa_workshop.repo;

import com.example.jpa_workshop.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findByUsername(String username);

    List<AppUser> findByRegistrationDateBetween(LocalDate start, LocalDate end);

    Optional<AppUser> findByDetails_Id(Long detailsId);

    Optional<AppUser> findByDetails_EmailIgnoreCase(String email);
}
