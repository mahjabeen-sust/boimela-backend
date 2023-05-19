package com.rest_api.fs14backend.author;
//
//
import com.rest_api.fs14backend.category.Category;
import org.springframework.beans.factory.annotation.Autowired;
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

  @GetMapping("/")
  public List<Author> getAuthors () {
    return authorService.getAllAuthors();
  }
//
  /*@GetMapping("/{id}")
  public Author getAuthorById(@PathVariable  UUID id) {
    return authorService.getAuthorById(id);
  }*/
  
  @PostMapping("/")
  public ResponseEntity<?> createOne(@RequestBody Author author) throws Exception {
    return authorService.createOne(author);
    
  }
  @DeleteMapping(value = "/{id}")
  public ResponseEntity<?> deleteAuthor(@PathVariable UUID id) throws Exception {
    return authorService.deleteAuthor(id);
  }
  
  @PutMapping(value = "/{id}")
  public ResponseEntity<?> updateAuthor(@PathVariable UUID id, @RequestBody Author author) throws Exception {
    return authorService.updateAuthor(id, author);
  }
  
}
