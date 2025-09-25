package com.example.jpa_workshop.repo;

import com.example.jpa_workshop.entity.BookLoan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface BookLoanRepository extends JpaRepository<BookLoan, Integer> {

    List<BookLoan> findByBorrower_Id(Long borrowerId);

    List<BookLoan> findByBook_Id(int bookId);

    // Loans not yet returned
    List<BookLoan> findByReturnedDateIsNull();

    // Overdue = dueDate < today and not returned
    List<BookLoan> findByDueDateBeforeAndReturnedDateIsNull(LocalDate today);

    List<BookLoan> findByLoanDateBetween(LocalDate start, LocalDate end);

    @Modifying
    @Query("UPDATE BookLoan bl SET bl.returnedDate = CURRENT_DATE WHERE bl.id = :loanId")
    int markAsReturned(int loanId);
}