package com.rest_api.fs14backend.loan;



import com.rest_api.fs14backend.book.Book;
import com.rest_api.fs14backend.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LoanRepository extends JpaRepository<Loan, UUID> {
	@Query("SELECT s FROM Loan s WHERE s.loanUser = ?1")
	List<Loan> findByUser(User user);
}
