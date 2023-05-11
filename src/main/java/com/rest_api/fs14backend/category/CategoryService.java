package com.rest_api.fs14backend.category;

import com.rest_api.fs14backend.author.Author;
import com.rest_api.fs14backend.book.Book;
import com.rest_api.fs14backend.book.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

  /*public Category createOne(Category category) {
    return categoryRepository.save(category);
  }*/
  
  public List<Category> getAllCategories() {
    return categoryRepository.findAll();
  }
  
  public Category createOne(Category newCategory) throws Exception {
    List<Category> categoryList = getAllCategories();
    for (Category category : categoryList) {
      if (newCategory.getName().equals(category.getName())) {
        throw new IllegalStateException("Category " + newCategory.getName() +  " already exist");
      }
    }
    return categoryRepository.save(newCategory);
  }

  public Category findById(UUID categoryId) {
    return categoryRepository.findById(categoryId).orElse(null);
  }
  
  
  
  /*public void deleteCategory(UUID id) {
    Optional<Category> categoryToDelete = categoryRepository.findCategoryById(id);
    if (categoryToDelete.isPresent()) {
      categoryRepository.delete(categoryToDelete.get());
    } else {
      throw new IllegalStateException("Category with " + id + " not found");
    }
  }*/
  
  
  public void deleteCategory(UUID categoryId) throws Exception {
    Book book = bookRepository.findAll()
                                      .stream()
                                      .filter(
                                          bookMatchedWithCategory -> Objects.equals(bookMatchedWithCategory.getCategory().getId(), categoryId))
                                      .findFirst()
                                      .orElse(null);
    
    if (null != book) {
      throw new Exception("Book is existed with this category");
    } else {
      categoryRepository.deleteById(categoryId);
    }
  }
  
  @Transactional
  public void updateCategory(UUID id, Category newCategory) throws Exception {
    Optional<Category> categoryToEdit = categoryRepository.findCategoryById(id);
    if (categoryToEdit.isPresent()) {
      categoryToEdit.get().setName(newCategory.getName());
      
    }else{
      throw new Exception("Category does not exist!");
    }
  }
}
