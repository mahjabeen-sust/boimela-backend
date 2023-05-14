package com.rest_api.fs14backend.loan;

import com.rest_api.fs14backend.author.Author;
import com.rest_api.fs14backend.book.Book;
import com.rest_api.fs14backend.book.BookDTO;
import com.rest_api.fs14backend.book.BookService;
import com.rest_api.fs14backend.category.Category;
import com.rest_api.fs14backend.user.User;
import com.rest_api.fs14backend.user.UserRepository;
import jakarta.transaction.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/loan")
public class LoanController {
	
	
	@Autowired
	private LoanService loanService;
	
	@Autowired
	private BookService bookService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private LoanMapper loanMapper;
	
	@GetMapping("/all")
	public List<Loan> getLoans() {
		return loanService.getAllLoans();
	}
	
	@GetMapping("/{username}")
	public List<Loan> findLoanByUsername(@PathVariable String  username){
		User user = userRepository.findByUsername(username);
		return loanService.findByUser(user);
	}
	
	@PostMapping("/")
	public Loan createOne(@RequestBody LoanDTO loanDTO) {
		Long isbn = loanDTO.getBookIsbn();
		String  username = loanDTO.getUsername();
		
		Book book = bookService.findForLoan(isbn);
		User loanUser = userRepository.findByUsername(username);
		Loan loan = loanMapper.newLoan(loanDTO, book, loanUser);
		
		return loanService.createOne(loan, book);
	}
	
	@PutMapping(value = "/{id}")
	public void returnLoan(@PathVariable UUID id) throws Exception{
		Optional<Loan> loan = loanService.findById(id);
		if(loan.isPresent()){
			
			loanService.returnLoan(loan);
		}else {
			throw new IllegalStateException("Loan with id " + id + " not found");
		}
		
	}
}
