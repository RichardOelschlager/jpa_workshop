package com.example.jpa_workshop;

import com.example.jpa_workshop.entity.Details;
import com.example.jpa_workshop.repo.DetailsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class DetailsRepositoryTests {

    @Autowired
    DetailsRepository repo;

    Details d1, d2;

    @BeforeEach
    void setup() {
        repo.deleteAll();

        d1 = Details.builder()
                .name("Charlie Brown")
                .email("charlie@example.com")
                .address("Street 3")
                .build();

        d2 = Details.builder()
                .name("David Black")
                .email("david@example.com")
                .address("Street 4")
                .build();

        repo.saveAll(List.of(d1, d2));
    }

    @Test
    void findByEmail_shouldReturnMatch() {
        assertThat(repo.findByEmail("charlie@example.com")).isPresent();
    }

    @Test
    void findByNameContaining_shouldFindSubstring() {
        List<Details> result = repo.findByNameContaining("Black");
        assertThat(result).hasSize(1)
                .first().extracting(Details::getName).isEqualTo("David Black");
    }

    @Test
    void findByNameIgnoreCase_shouldIgnoreCase() {
        List<Details> result = repo.findByNameIgnoreCase("charlie brown");
        assertThat(result).hasSize(1);
    }
}