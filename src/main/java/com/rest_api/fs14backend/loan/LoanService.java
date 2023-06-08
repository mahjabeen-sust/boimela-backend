package com.rest_api.fs14backend.loan;

import com.rest_api.fs14backend.book.Book;
import com.rest_api.fs14backend.book.BookService;
import com.rest_api.fs14backend.user.User;
import com.rest_api.fs14backend.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LoanService {
	@Autowired
	private LoanRepository loanRepository;
	private UserRepository userRepository;
	
	@Autowired
	private BookService bookService;
	
	public List<Loan> getAllLoans() {
		return loanRepository.findAll();
	}
	
	public Optional<Loan> findById(UUID id) {
		return loanRepository.findById(id);
	}
	
	public List<Loan> findLoanByUser(User user){return loanRepository.findByUser(user);}
	
	@Transactional
	public Loan createOne(Loan loan, Book book){
		
		Loan savedLoan= loanRepository.save(loan);
		//update book status
		bookService.changeStatusForLoan(book, Book.Status.BORROWED);
		return savedLoan;
	}
	
	@Transactional
	public Loan returnLoan(Optional<Loan> loan){
		loan.get().setReturnDate(LocalDate.now());
		loan.get().setLoanStatus(Loan.LoanStatus.RETURNED);
		Book returnedBook = loan.get().getBook();
		returnedBook.setStatus(Book.Status.AVAILABLE);
		return loan.get();
	}
	
	public Loan ifBookIsInLoan(Book bookToDelete){
		return loanRepository.findAll()
							 .stream()
							 .filter(
									 bookMatchedWithLoan -> bookMatchedWithLoan.getBook().getId()==bookToDelete.getId() && bookMatchedWithLoan.getLoanStatus().toString()=="INDEBT")
							 .findFirst()
							 .orElse(null);
	}
}
