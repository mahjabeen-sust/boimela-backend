package com.rest_api.fs14backend.book;

import com.rest_api.fs14backend.author.Author;
import com.rest_api.fs14backend.category.Category;
import com.rest_api.fs14backend.exceptions.CustomException;
import com.rest_api.fs14backend.loan.Loan;
import com.rest_api.fs14backend.loan.LoanRepository;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookService {
  @Autowired
  private  BookRepository bookRepository;
  
  @Autowired
  private LoanRepository loanRepository;

  

  public List<Book> getAllBooks() {
    return bookRepository.findAll();
  }
  
  
  public Book addBook(Book newBook) throws Exception{
    try {
      return bookRepository.save(newBook);
    } catch (DataAccessException e) {
      // Log the entire exception stack trace
      // e.printStackTrace(); // This will show you the hierarchy of exceptions
      
      // Get the root cause
      Throwable cause = e.getRootCause();
      
      // Traverse causes to find PSQLException
      while (cause != null) {
        if (cause instanceof PSQLException) {
          String errorMessage = cause.getMessage();
          throw new CustomException("Database Error: " + errorMessage, cause);
        }
        cause = cause.getCause(); // Move to the next cause
      }
      
      // If no PSQLException found, throw a generic custom exception
      throw new CustomException(e.getMessage(), e);
    }
    
  }

  public Optional<Book> findById(Long isbn) {
    return bookRepository.findBookByISBN(isbn);
  }
  
  
  
  public Book findForLoan(Long isbn){return bookRepository.findForLoan(isbn);}
  
  
  public boolean deleteBook(Long isbn) throws Exception{
    Optional<Book> bookToDelete=bookRepository.findBookByISBN(isbn);
    if(bookToDelete.isPresent()){
      bookRepository.delete(bookToDelete.get());
      return true;
    }else{
      return false;
    }
  }
  
  @Transactional
  public Book updateBook(Optional<Book> bookToEdit, Book newBook) throws Exception {
    
    try {
      bookToEdit.get().setCategory(newBook.getCategory());
      bookToEdit.get().setTitle(newBook.getTitle());
      bookToEdit.get().setDescription(newBook.getDescription());
      bookToEdit.get().setAuthorList(newBook.getAuthorList());
      bookToEdit.get().setPublishedDate(newBook.getPublishedDate());
      bookToEdit.get().setPublishers(newBook.getPublishers());
      bookToEdit.get().setStatus(newBook.getStatus());
      return bookToEdit.get();
    } catch (DataAccessException e) {
      // Log the entire exception stack trace
      // e.printStackTrace(); // This will show you the hierarchy of exceptions
      
      // Get the root cause
      Throwable cause = e.getRootCause();
      
      // Traverse causes to find PSQLException
      while (cause != null) {
        if (cause instanceof PSQLException) {
          String errorMessage = cause.getMessage();
          throw new CustomException("Database Error: " + errorMessage, cause);
        }
        cause = cause.getCause(); // Move to the next cause
      }
      
      // If no PSQLException found, throw a generic custom exception
      throw new CustomException(e.getMessage(), e);
    }
  }
  
  @Transactional
  public void changeStatusForLoan(Book bookToLoan, Book.Status status){
    bookToLoan.setStatus(status);
  }
  
  //checking if book exists with category
  public Book ifBookHasCategory(UUID categoryId){
    return bookRepository.findAll()
               .stream()
               .filter(
                   bookMatchedWithCategory -> Objects.equals(bookMatchedWithCategory.getCategory().getId(), categoryId))
               .findFirst()
               .orElse(null);
    
  }
  
  public Book ifBookHasAuthor(Author authorToDelete){
    return bookRepository.findAll()
                           .stream()
                           .filter(
                               bookMatchedWithAuthor -> bookMatchedWithAuthor.getAuthorList().contains(authorToDelete))
                           .findFirst()
                           .orElse(null);
    
  }
  
}
