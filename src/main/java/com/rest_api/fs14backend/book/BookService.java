package com.rest_api.fs14backend.book;

import com.rest_api.fs14backend.todo.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
  @Autowired
  private  BookRepository bookRepository;

  

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
      bookRepository.delete(bookToDelete.get());
      return ResponseEntity.status(HttpStatus.ACCEPTED).body("Book with isbn " + isbn + " deleted successfully");
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
}
