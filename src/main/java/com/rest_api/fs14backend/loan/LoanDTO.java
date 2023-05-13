package com.rest_api.fs14backend.loan;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor
@Data
public class LoanDTO {
	private LocalDate borrowDate;
	private LocalDate returnDate;
	private Loan.LoanStatus loanStatus;
	private Long bookIsbn;
	private String username;
	
}
