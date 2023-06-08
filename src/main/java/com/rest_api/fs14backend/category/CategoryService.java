package com.rest_api.fs14backend.category;

import com.rest_api.fs14backend.author.Author;
import com.rest_api.fs14backend.book.Book;
import com.rest_api.fs14backend.book.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class CategoryService {
  
  @Autowired
  private BookRepository bookRepository;
  @Autowired
  private CategoryRepository categoryRepository;

  
  
  public List<Category> getAllCategories() {
    return categoryRepository.findAll();
  }
  
  
  public boolean checkCategoryExists(String categoryName) {
    // Check if the category already exists in the repository
    return categoryRepository.existsByName(categoryName);
  }
  
  public Category addCategory(String  categoryName) throws Exception{
    try{
      Category newCategory=new Category();
      newCategory.setName(categoryName);
      return categoryRepository.save(newCategory);
    }catch (Exception e){
      System.out.println("Failed to create category!");
      throw new RuntimeException("Failed to create category", e);
    }
    
  }

  public Category findById(UUID categoryId) {
    return categoryRepository.findById(categoryId).orElse(null);
  }
  
  public Category deleteCategory(UUID id) throws Exception {
    Optional<Category> categoryToEdit=categoryRepository.findById(id);
    if(categoryToEdit.isPresent()){
      Category category=categoryToEdit.get();
      categoryRepository.delete(category);
      return category;
    }else{
      return null;
    }
    
  }
  
  
  
  @Transactional
  public Category updateCategory(UUID id, CategoryDTO newCategory) throws Exception {
      Optional<Category> categoryToEdit=categoryRepository.findById(id);
      if(categoryToEdit.isPresent()){
        Category category=categoryToEdit.get();
        category.setName(newCategory.getName());
        return categoryRepository.save(category);
      }else{
        return null;
      }
  }
}
