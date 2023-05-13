package com.rest_api.fs14backend.book;

import com.rest_api.fs14backend.todo.Todo;
import org.springframework.beans.factory.annotation.Autowired;
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
  
  public Book createOne(Book book) {
    return bookRepository.save(book);
  }

  public Optional<Book> findById(Long isbn) {
    return bookRepository.findBookByISBN(isbn);
  }
  
  public Book findForLoan(Long isbn){return bookRepository.findForLoan(isbn);}

  public void deleteBook(Long isbn) throws Exception {
    Optional<Book> bookToDelete = bookRepository.findBookByISBN(isbn);
    if (bookToDelete.isPresent()) {
      bookRepository.delete(bookToDelete.get());
    } else {
      throw new IllegalStateException("Book with isbn " + isbn + " not found");
    }
  }

  //update with all properties and (not implemented yet possible null values)
  @Transactional
  public void updateBook(Long isbn, Book newBook) throws Exception {
    Optional<Book> bookToEdit = bookRepository.findBookByISBN(isbn);
    if (bookToEdit.isPresent()) {
      bookToEdit.get().setCategory(newBook.getCategory());
      bookToEdit.get().setTitle(newBook.getTitle());
      bookToEdit.get().setDescription(newBook.getDescription());
      bookToEdit.get().setPublishedDate(newBook.getPublishedDate());
      bookToEdit.get().setPublishers(newBook.getPublishers());
      bookToEdit.get().setStatus(newBook.getStatus());
    }else{
      throw new Exception("Book does not exist!");
    }
  }
  
  @Transactional
  public void changeStatusForLoan(Book bookToLoan, Book.Status status){
    bookToLoan.setStatus(status);
  }
}
