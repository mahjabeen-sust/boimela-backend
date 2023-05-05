package com.rest_api.fs14backend.author;
//
//
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//import java.util.UUID;
//
//@RestController
//@RequestMapping("api/v1/authors")
//public class AuthorController {
//  private final AuthorService authorService;
//
//  public AuthorController(AuthorService authorService) {
//    this.authorService = authorService;
//  }
//
//  @GetMapping
//  public List<Author> getAuthors () {
//    return authorService.getAllAuthors();
//  }
//
//  @GetMapping("/{id}")
//  public Author getAuthorById(@PathVariable  UUID id) {
//    return authorService.getAuthorById(id);
//  }
//}
