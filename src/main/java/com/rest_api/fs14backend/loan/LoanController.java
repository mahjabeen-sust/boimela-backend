package com.rest_api.fs14backend.loan;

import com.rest_api.fs14backend.SecurityConfig.CustomUserDetailsService;
import com.rest_api.fs14backend.author.Author;
import com.rest_api.fs14backend.book.Book;
import com.rest_api.fs14backend.book.BookDTO;
import com.rest_api.fs14backend.book.BookService;
import com.rest_api.fs14backend.category.Category;
import com.rest_api.fs14backend.user.User;
import com.rest_api.fs14backend.user.UserRepository;
import com.rest_api.fs14backend.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@GetMapping("/all")
	public ResponseEntity<List<Loan>> getLoans() {
		return ResponseEntity.ok(loanService.getAllLoans());
	}
	
	@GetMapping("/{username}")
	public ResponseEntity<?> findLoanByUsername(@PathVariable String  username, HttpServletRequest request) {
		String authorizationHeader = request.getHeader("Authorization");
		String token = authorizationHeader.substring(7);
		String loggedInUser = jwtUtils.extractUsername(token);
		if(!loggedInUser.equals(username)){
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized!");
		}else{
			User user= userRepository.findByUsername(username);
			//System.out.println("entered else");
			List<Loan> loans=loanService.findLoanByUser(user);
			return ResponseEntity.ok(loans);
		}
		
		
	}
	
	@PostMapping("/")
	public ResponseEntity<?> createOne(@RequestBody LoanDTO loanDTO) {
		Long isbn = loanDTO.getBookIsbn();
		String  username = loanDTO.getUsername();
		
		Book book = bookService.findForLoan(isbn);
		User loanUser = userRepository.findByUsername(username);
		Loan loan = loanMapper.newLoan(loanDTO, book, loanUser);
		Loan savedLoan = loanService.createOne(loan, book);
		
		return ResponseEntity.ok(savedLoan);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> returnLoan(@PathVariable UUID id, @RequestBody String text){
		Optional<Loan> loan = loanService.findById(id);
		if(loan.isPresent()){
			Loan returnedLoan=loanService.returnLoan(loan);
			return ResponseEntity.ok(returnedLoan);
		}else {
			//throw new IllegalStateException("Loan with id " + id + " not found");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Loan with id " + id + " not found");
		}
		
	}
}
