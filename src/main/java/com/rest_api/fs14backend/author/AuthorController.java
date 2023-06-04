package com.rest_api.fs14backend.author;
//
//
import com.rest_api.fs14backend.category.Category;
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

  @GetMapping("/")
  public List<Author> getAuthors () {
    return authorService.getAllAuthors();
  }

  
  @PostMapping("/")
  public ResponseEntity<?> createOne(@RequestBody AuthorDTO authorDTO) throws Exception {
    Author author=new Author();
    author.setName(authorDTO.getName());
    return authorService.createOne(author);
    
  }
  

  
  @DeleteMapping(value = "/{id}")
  public ResponseEntity<?> deleteAuthor(@PathVariable UUID id) throws Exception {
    return authorService.deleteAuthor(id);
  }
  
  @PutMapping(value = "/{id}")
  public ResponseEntity<?> updateAuthor(@PathVariable UUID id, @RequestBody AuthorDTO authorDTO) throws Exception {
    return authorService.updateAuthor(id, authorDTO);
  }
  
}
