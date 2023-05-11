package com.rest_api.fs14backend.author;
//

import com.rest_api.fs14backend.book.Book;
import com.rest_api.fs14backend.book.BookRepository;


import com.rest_api.fs14backend.category.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//
import java.util.List;
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
  
  
  public Author createOne(Author newAuthor) {
    List<Author> authorList = getAllAuthors();
    for (Author author : authorList) {
      if (newAuthor.getName().equals(author.getName())) {
        throw new IllegalStateException("Author " + newAuthor.getName() +  " already exist");
      }
    }
    return authorRepository.save(newAuthor);
  }
  
  public void deleteAuthor(UUID id) throws Exception {
    Optional<Author> authorToDelete = authorRepository.findAuthorById(id);
    if (authorToDelete.isPresent()) {
      authorRepository.delete(authorToDelete.get());
    } else {
      throw new IllegalStateException("Author with id " + id + " not found");
    }
  }
  
  //not working
  public void updateAuthor(UUID id, Author newAuthor) throws Exception {
    Optional<Author> authorToEdit = authorRepository.findAuthorById(id);
    System.out.println("authorToEdit : "+authorToEdit);
    if (authorToEdit.isPresent()) {
      System.out.println("edit name : "+newAuthor.getName());
      authorToEdit.get().setName(newAuthor.getName());
      
    }else{
      throw new Exception("Author does not exist!");
    }
  }

}

