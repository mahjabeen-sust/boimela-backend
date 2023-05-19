package com.rest_api.fs14backend.author;
//

import com.rest_api.fs14backend.book.Book;
import com.rest_api.fs14backend.book.BookRepository;


import com.rest_api.fs14backend.category.Category;
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

 
//
/*  public Author createOne(Author author) {
  return authorRepository.save(author);
}*/
  
  public Author findById(UUID authorId) {
    return authorRepository.findById(authorId).orElse(null);
  }
  public List<Author> getAllAuthors() {
    return authorRepository.findAll();
  }
  
  
  public ResponseEntity<?> createOne(Author newAuthor) {
    List<Author> authorList = getAllAuthors();
    for (Author author : authorList) {
      if (newAuthor.getName().equals(author.getName())) {
        //throw new IllegalStateException("Author " + newAuthor.getName() +  " already exist");
        //return HttpStatus.CONFLICT.toString();
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Author " + newAuthor.getName() +  " already exists");
      }
    }
    Author savedAuthor = authorRepository.save(newAuthor);
    return ResponseEntity.ok(savedAuthor);
    
    //return HttpStatus.CREATED.toString();
  }
  
  public ResponseEntity<?> deleteAuthor(UUID id) throws Exception {
    Optional<Author> authorToDelete = authorRepository.findAuthorById(id);
    if (authorToDelete.isPresent()) {
      Book book = bookRepository.findAll()
                      .stream()
                      .filter(
                          bookMatchedWithAuthor -> bookMatchedWithAuthor.getAuthorList().contains(authorToDelete.get()))
                      .findFirst()
                      .orElse(null);
      
      if (null != book) {
        //throw new Exception("Book is existed with this author");
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Book exists with this author");
      } else {
        authorRepository.delete(authorToDelete.get());
        return ResponseEntity.ok(authorToDelete);
      }
      
    } else {
      //throw new IllegalStateException("Author with id " + id + " not found");
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Author with id " + id + " not found");
      
    }
    
    
  }
  
  //not working
  @Transactional
  public ResponseEntity<?> updateAuthor(UUID id, Author newAuthor) throws Exception {
    Optional<Author> authorToEdit = authorRepository.findAuthorById(id);
    //System.out.println("authorToEdit : "+authorToEdit);
    if (authorToEdit.isPresent()) {
      //System.out.println("edit name : "+newAuthor.getName());
      List<Author> authorList = getAllAuthors();
      for (Author author : authorList) {
        if (newAuthor.getName().equals(author.getName())) {
          return ResponseEntity.status(HttpStatus.CONFLICT).body("Author " + newAuthor.getName() +  " already exists");
        }
      }
      authorToEdit.get().setName(newAuthor.getName());
      return ResponseEntity.ok(authorToEdit);
      
    }else{
      //throw new Exception("Author does not exist!");
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Author does not exist!");
    }
  }

}

