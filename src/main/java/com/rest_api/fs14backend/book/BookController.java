package com.rest_api.fs14backend.book;


import com.rest_api.fs14backend.author.Author;
import com.rest_api.fs14backend.author.AuthorService;
import com.rest_api.fs14backend.category.Category;
import com.rest_api.fs14backend.category.CategoryService;
import com.rest_api.fs14backend.todo.Todo;
import com.rest_api.fs14backend.todo.TodoDTO;
import com.rest_api.fs14backend.todo.TodoMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
  private BookMapper bookMapper;

  

  @GetMapping("/")
  public List<Book> getBooks() {
    return bookService.getAllBooks();
  }

  @GetMapping(value = "/{isbn}")
  public Optional<Book> getBookByIsbn(@PathVariable Long isbn) {
    return bookService.findById(isbn);
  }

  @DeleteMapping(value = "/{isbn}")
  public ResponseEntity<?> deleteBook(@PathVariable Long isbn) throws Exception {
    return bookService.deleteBook(isbn);
  }

  /*@PostMapping
  public void createOne(@RequestBody Book book) {
    bookService.addOneBook(book);
  }*/
  
  //creating a new book
  @PostMapping("/")
  public ResponseEntity<?> createOne(@RequestBody BookDTO bookDTO) {
    UUID categoryId = bookDTO.getCategoryId();
    List<UUID> authorIdList = bookDTO.getAuthorIdList();
    Category category = categoryService.findById(categoryId);
    //List<B> bList = aList.stream().map(A::getB).collect(Collectors.toList());
    List<Author> authorList = new ArrayList<>();
    authorIdList.forEach(a -> authorList.add(authorService.findById(a)));
    
    Book book = bookMapper.newBook(bookDTO, category, authorList);
    
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
  
  /*@PutMapping(value = "/{isbn}")
  public void updateBook(@PathVariable Long isbn, @RequestBody Book book) throws Exception {
    bookService.updateBook(isbn, book);
  }*/
  
  @PutMapping(value = "/{isbn}")
  public ResponseEntity<?> updateBook(@PathVariable Long isbn, @RequestBody BookDTO bookDTO) throws Exception {
    UUID categoryId = bookDTO.getCategoryId();
    Category category = categoryService.findById(categoryId);
    List<UUID> authorIdList = bookDTO.getAuthorIdList();
    List<Author> authorList = new ArrayList<>();
    authorIdList.forEach(a -> authorList.add(authorService.findById(a)));
    
    Book book = bookMapper.newBook(bookDTO, category, authorList);
    return bookService.updateBook(isbn, book);
  }
}
