package com.rest_api.fs14backend.loan;

import com.rest_api.fs14backend.book.Book;
import com.rest_api.fs14backend.user.User;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class LoanMapper {
	public Loan newLoan(LoanDTO loan, Book book, User user){
		return new Loan(
				
				loan.getBorrowDate(),
				loan.getReturnDate(),
				loan.getLoanStatus(),
				book,
				user
				
		);
	}
}
