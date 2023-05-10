package com.rest_api.fs14backend.book;


import com.rest_api.fs14backend.category.Category;
import com.rest_api.fs14backend.category.CategoryService;
import com.rest_api.fs14backend.todo.Todo;
import com.rest_api.fs14backend.todo.TodoDTO;
import com.rest_api.fs14backend.todo.TodoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
  private BookMapper bookMapper;

  

  @GetMapping("/")
  public List<Book> getBooks() {
    return bookService.getAllBooks();
  }

  @GetMapping(value = "/{isbn}")
  public Optional<Book> getBookByIsbn(@PathVariable Long isbn) {
    return bookService.getBookById(isbn);
  }

  @DeleteMapping(value = "/{isbn}")
  public void deleteBook(@PathVariable Long isbn) {
    bookService.deleteBook(isbn);
  }

  /*@PostMapping
  public void createOne(@RequestBody Book book) {
    bookService.addOneBook(book);
  }*/
  
  //creating a new book
  @PostMapping("/")
  public Book createOne(@RequestBody BookDTO bookDTO) {
    UUID categoryId = bookDTO.getCategoryId();
    Category category = categoryService.findById(categoryId);
    
    Book book = bookMapper.newBook(bookDTO, category);
    
    return bookService.createOne(book);
  }
  
  //json value for post method
  /*{
    "categoryId":"81d1b149-0f37-4c21-aca8-69d6975aee43",
     "isbn":"1234",
     "title":"A Haunted House",
     "publishedDate":"2000-10-31",
     "description":"Very creepy",
     "status":"AVAILABLE",
     "publishers":"QP"
  }*/
  
  @PutMapping(value = "/{isbn}")
  public void updateBook(@PathVariable Long isbn, @RequestBody Book book) {
    bookService.updateBook(isbn, book);
  }
}
