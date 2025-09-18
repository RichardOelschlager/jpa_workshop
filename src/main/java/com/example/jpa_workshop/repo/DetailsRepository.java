package com.example.jpa_workshop.repo;

import com.example.jpa_workshop.entity.Details;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DetailsRepository extends JpaRepository<Details, Long> {

    Optional<Details> findByEmail(String email);

    List<Details> findByNameContaining(String part);

    List<Details> findByNameIgnoreCase(String name);
}
