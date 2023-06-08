package com.rest_api.fs14backend.author;
//
//
import com.rest_api.fs14backend.book.Book;
import com.rest_api.fs14backend.book.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
//
import java.util.List;
import java.util.UUID;
//
@RestController
@RequestMapping("api/v1/authors")
public class AuthorController {
  @Autowired
  private AuthorService authorService;
  
  @Autowired
  private BookService bookService;

  @GetMapping("/")
  public ResponseEntity<List<Author>> getAuthors () {
    return ResponseEntity.ok(authorService.getAllAuthors());
  }

  

  
  @PostMapping("/")
  public ResponseEntity<?> createOne(@RequestBody AuthorDTO authorDTO) throws Exception{
    String authorName = authorDTO.getName();
    
    // Delegate the task of adding an author to the service class
    boolean authorExists = authorService.checkAuthorExists(authorName);
    if (authorExists) {
      return ResponseEntity.badRequest().body("Author " + authorName + " already exists");
    }
    Author savedAuthor = authorService.addAuthor(authorName);
    return ResponseEntity.ok(savedAuthor);
  }
  
 
  
  @DeleteMapping(value = "/{id}")
  public ResponseEntity<?> deleteAuthor(@PathVariable UUID id) throws Exception {
    Author authorToDelete=authorService.findById(id);
    Book book=bookService.ifBookHasAuthor(authorToDelete);
    if (null != book) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body("Book exists with this author");
    }
    Author deletedAuthor=authorService.deleteAuthor(id);
    if(deletedAuthor!=null){
      return ResponseEntity.ok(deletedAuthor);
    }else{
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Author with id " + id + " not found");
    }
    
  }
 
  
  @PutMapping(value = "/{id}")
  public ResponseEntity<?> updateAuthor(@PathVariable UUID id, @RequestBody AuthorDTO authorDTO) throws Exception {
    boolean authorExists = authorService.checkAuthorExists(authorDTO.getName());
    if (authorExists) {
      return ResponseEntity.badRequest().body("Author " + authorDTO.getName()+ " already exists");
    }
    Author updatedAuthor = authorService.updateAuthor(id, authorDTO);
    if(updatedAuthor!=null){
      return ResponseEntity.ok(updatedAuthor);
    }else{
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Author with id " + id + " not found");
    }
    
  }
  
}
