package com.example.jpa_workshop;

import com.example.jpa_workshop.entity.AppUser;
import com.example.jpa_workshop.entity.Details;
import com.example.jpa_workshop.repo.AppUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;



@DataJpaTest
class AppUserRepositoryTests {

    @Autowired
    AppUserRepository repo;

    AppUser user1, user2;

    @BeforeEach
    void setup() {
        repo.deleteAll();

        Details d1 = Details.builder()
                .name("Alice")
                .email("alice@example.com")
                .address("Street 1")
                .build();

        Details d2 = Details.builder()
                .name("Bob")
                .email("bob@example.com")
                .address("Street 2")
                .build();

        user1 = AppUser.builder()
                .username("alice123")
                .registrationDate(LocalDate.of(2023, 1, 15))
                .details(d1)
                .build();

        user2 = AppUser.builder()
                .username("bob456")
                .registrationDate(LocalDate.of(2023, 5, 10))
                .details(d2)
                .build();

        repo.saveAll(List.of(user1, user2));
    }

    @Test
    void findByUsername_shouldReturnCorrectUser() {
        assertThat(repo.findByUsername("alice123")).isPresent()
                .get().extracting(AppUser::getUsername).isEqualTo("alice123");
    }

    @Test
    void findByDetailsId_shouldWork() {
        Long detailsId = user1.getDetails().getId();
        assertThat(repo.findByDetails_Id(detailsId)).isPresent()
                .get().extracting(u -> u.getDetails().getEmail()).isEqualTo("alice@example.com");
    }

    @Test
    void findByDetailsEmailIgnoreCase_shouldIgnoreCase() {
        assertThat(repo.findByDetails_EmailIgnoreCase("ALICE@EXAMPLE.COM")).isPresent();
    }

    @Test
    void findByRegistrationDateBetween_shouldFilterCorrectly() {
        List<AppUser> result = repo.findByRegistrationDateBetween(
                LocalDate.of(2023, 1, 1),
                LocalDate.of(2023, 3, 1)
        );
        assertThat(result).extracting(AppUser::getUsername).containsExactly("alice123");
    }
}