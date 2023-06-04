package com.rest_api.fs14backend.loan;

import com.rest_api.fs14backend.book.Book;
import com.rest_api.fs14backend.user.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Entity
@Table(name="loan")
@Data
@NoArgsConstructor
public class Loan {
	
	@Id
	@GeneratedValue
	@UuidGenerator
	private UUID id;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User loanUser;
	
	@ManyToOne
	@JoinColumn(name = "book_id")
	private Book book;
	
	@Column(nullable = false)
	private LocalDate borrowDate;
	
	@Column(nullable = true)
	private LocalDate returnDate;
	
	public enum LoanStatus {
		INDEBT,
		RETURNED
	}
	
	private LoanStatus loanStatus;
	public Loan(LocalDate borrowDate, LocalDate returnDate,LoanStatus loanStatus, Book book, User loanUser) {
		
		this.borrowDate = borrowDate;
		this.returnDate = returnDate;
		this.loanStatus = loanStatus;
		this.book = book;
		this.loanUser = loanUser;
		
	}
	
	
}
