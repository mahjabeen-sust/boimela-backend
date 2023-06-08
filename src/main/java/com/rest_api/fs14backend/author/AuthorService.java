package com.rest_api.fs14backend.author;
//

import com.rest_api.fs14backend.book.Book;
import com.rest_api.fs14backend.book.BookRepository;


import com.rest_api.fs14backend.category.Category;
import com.rest_api.fs14backend.category.CategoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
//
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
//
@Service

public class AuthorService {
  @Autowired
  private AuthorRepository authorRepository;
  @Autowired
  private BookRepository bookRepository;


  
  public Author findById(UUID authorId) {
    return authorRepository.findById(authorId).orElse(null);
  }
  public List<Author> getAllAuthors() {
    return authorRepository.findAll();
  }
  
  public boolean checkAuthorExists(String authorName) {
    // Check if the author already exists in the repository
    return authorRepository.existsByName(authorName);
  }
  
  public Author addAuthor(String  authorName) throws Exception{
    try{
      Author newAuthor=new Author();
      newAuthor.setName(authorName);
      return authorRepository.save(newAuthor);
    }catch (Exception e){
      System.out.println("Failed to create author!");
      throw new RuntimeException("Failed to create author", e);
    }
    
  }
  
 
  
  public Author deleteAuthor(UUID id) throws Exception {
    Optional<Author> authorToDelete=authorRepository.findById(id);
    if(authorToDelete.isPresent()){
      Author author=authorToDelete.get();
      authorRepository.delete(author);
      return author;
    }else{
      return null;
    }
    
  }

  
  
  @Transactional
  public Author updateAuthor(UUID id, AuthorDTO newAuthor) throws Exception {
    Optional<Author> authorToEdit=authorRepository.findById(id);
    if(authorToEdit.isPresent()){
      Author author=authorToEdit.get();
      author.setName(newAuthor.getName());
      return authorRepository.save(author);
    }else{
      return null;
    }
  }

}

