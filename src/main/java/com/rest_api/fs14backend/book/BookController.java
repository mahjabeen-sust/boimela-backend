package com.rest_api.fs14backend.book;


import com.rest_api.fs14backend.author.Author;
import com.rest_api.fs14backend.author.AuthorService;
import com.rest_api.fs14backend.category.Category;
import com.rest_api.fs14backend.category.CategoryDTO;
import com.rest_api.fs14backend.category.CategoryService;

import com.rest_api.fs14backend.loan.Loan;
import com.rest_api.fs14backend.loan.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/books")
public class BookController {
  @Autowired
  private BookService bookService;

  @Autowired
  private CategoryService categoryService;

  @Autowired
  private AuthorService authorService;

  @Autowired
  private LoanService loanService;

  @Autowired
  private BookMapper bookMapper;



  @GetMapping("/")
  public ResponseEntity<List<Book>> getBooks() {
    return ResponseEntity.ok(bookService.getAllBooks());
  }

  @GetMapping(value = "/{isbn}")
  public Optional<Book> getBookByIsbn(@PathVariable Long isbn) {
    return bookService.findById(isbn);
  }


  @DeleteMapping(value = "/{isbn}")
  public ResponseEntity<?> deleteBook(@PathVariable Long isbn) throws Exception {
    Optional<Book> bookToDelete=bookService.findById(isbn);
    if(!bookToDelete.isPresent()){
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book with isbn " + isbn + " not found!");
    }

    Loan bookIsInLoan=loanService.ifBookIsInLoan(bookToDelete.get());
    if(bookIsInLoan!=null){
        return ResponseEntity.badRequest().body("Book is in loan!");
    }else{
      try{
        boolean deletedBook=bookService.deleteBook(isbn);
        if(deletedBook){
          return ResponseEntity.ok(isbn);
        }else{
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                  .body("Failed to delete the book.");
        }
      } catch(Exception e){
        return new ResponseEntity<>("Failed to delete book: " + e.getMessage(), HttpStatus.FORBIDDEN);
      }
    }
  }


  @PostMapping("/")
  public ResponseEntity<?> createOne(@RequestBody BookDTO bookDTO) throws Exception{
    Optional<Book> bookToAdd = bookService.findById(bookDTO.getISBN());

    if(bookToAdd.isPresent()){
      return ResponseEntity.status(HttpStatus.CONFLICT).body("Book with isbn " + bookDTO.getISBN() + " already exists!");
    }else{
      try {
        UUID categoryId = bookDTO.getCategoryId();
        List<UUID> authorIdList = bookDTO.getAuthorIdList();
        Category category = categoryService.findById(categoryId);
        List<Author> authorList = new ArrayList<>();
        authorIdList.forEach(a -> authorList.add(authorService.findById(a)));

        Book book = bookMapper.newBook(bookDTO, category, authorList);
        Book savedBook = bookService.addBook(book);

        if (savedBook != null) {
          return ResponseEntity.ok(savedBook);
        } else {
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                     .body("Failed to save the book.");
        }
      } catch (Exception ex) {
        return new ResponseEntity<>("Failed to create book: " + ex.getMessage(), HttpStatus.FORBIDDEN);
      }
    }

  }


  @PutMapping(value = "/{isbn}")
  public ResponseEntity<?> updateBook(@PathVariable Long isbn, @RequestBody BookDTO bookDTO) throws Exception {
    Optional<Book> bookToEdit = bookService.findById(isbn);
    
    if(!bookToEdit.isPresent()){
      return ResponseEntity.status(HttpStatus.CONFLICT).body("Book with isbn " + bookDTO.getISBN() + " does not exists!");
    } else {
      try {
        UUID categoryId = bookDTO.getCategoryId();
        Category category = categoryService.findById(categoryId);
        List<UUID> authorIdList = bookDTO.getAuthorIdList();
        List<Author> authorList = new ArrayList<>();
        authorIdList.forEach(a -> authorList.add(authorService.findById(a)));
    
        Book book = bookMapper.newBook(bookDTO, category, authorList);
        Book updatedBook= bookService.updateBook(bookToEdit, book);
        if(updatedBook!=null){
          return ResponseEntity.ok(updatedBook);
        }else{
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                     .body("Failed to save the book.");
        }
      } catch (Exception ex) {
        return new ResponseEntity<>("Failed to update book: " + ex.getMessage(), HttpStatus.FORBIDDEN);
      }
    }
  }
}
