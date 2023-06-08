package com.rest_api.fs14backend.book;

import com.rest_api.fs14backend.author.Author;
import com.rest_api.fs14backend.loan.Loan;
import com.rest_api.fs14backend.loan.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
  
  public ResponseEntity<?> createOne(Book book) {
    Optional<Book> bookToAdd = bookRepository.findBookByISBN(book.getISBN());
    if(bookToAdd.isPresent()){
      return ResponseEntity.status(HttpStatus.CONFLICT).body("Book with isbn " + book.getISBN() + " already exists!");
    }
    Book savedBook = bookRepository.save(book);
    return ResponseEntity.ok(savedBook);
  }

  public Optional<Book> findById(Long isbn) {
    return bookRepository.findBookByISBN(isbn);
  }
  
  
  
  public Book findForLoan(Long isbn){return bookRepository.findForLoan(isbn);}

  public ResponseEntity<?> deleteBook(Long isbn) throws Exception {
    Optional<Book> bookToDelete = bookRepository.findBookByISBN(isbn);
    if (bookToDelete.isPresent()) {
      
      Loan loan = loanRepository.findAll()
                      .stream()
                      .filter(
                          bookMatchedWithLoan -> bookMatchedWithLoan.getBook().getId()==bookToDelete.get().getId())
                      .findFirst()
                      .orElse(null);
      
      if (null != loan) {
        //throw new Exception("Book is existed with this author");
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Book is in loan");
      } else {
        bookRepository.delete(bookToDelete.get());
        return ResponseEntity.ok(isbn);
      }
      
      
    } else {
      //throw new IllegalStateException("Book with isbn " + isbn + " not found");
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book with isbn " + isbn + " not found");
    }
  }

  //update with all properties and (not implemented yet possible null values)
  @Transactional
  public ResponseEntity<?> updateBook(Long isbn, Book newBook) throws Exception {
    Optional<Book> bookToEdit = bookRepository.findBookByISBN(isbn);
    if (bookToEdit.isPresent()) {
      bookToEdit.get().setCategory(newBook.getCategory());
      bookToEdit.get().setTitle(newBook.getTitle());
      bookToEdit.get().setDescription(newBook.getDescription());
      bookToEdit.get().setAuthorList(newBook.getAuthorList());
      bookToEdit.get().setPublishedDate(newBook.getPublishedDate());
      bookToEdit.get().setPublishers(newBook.getPublishers());
      bookToEdit.get().setStatus(newBook.getStatus());
      return ResponseEntity.ok(bookToEdit);
    }else{
      //throw new Exception("Book does not exist!");
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book does not exist!");
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
