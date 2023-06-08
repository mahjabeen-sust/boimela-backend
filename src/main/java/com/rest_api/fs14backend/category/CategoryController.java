package com.rest_api.fs14backend.category;

import com.rest_api.fs14backend.book.Book;
import com.rest_api.fs14backend.book.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/categories")
public class CategoryController {

  
  @Autowired
  private CategoryService categoryService;
  
  @Autowired
  private BookService bookService;
  
  @PostMapping("/")
  public ResponseEntity<?> createOne(@RequestBody CategoryDTO categoryDTO) throws Exception{
    String categoryName = categoryDTO.getName();
    
    // Delegate the task of adding a category to the service class
    boolean categoryExists = categoryService.checkCategoryExists(categoryName);
    if (categoryExists) {
      return ResponseEntity.badRequest().body("Category " + categoryName + " already exists");
    }
    Category savedCategory = categoryService.addCategory(categoryName);
    return ResponseEntity.ok(savedCategory);
  }

  @GetMapping("/")
  public ResponseEntity<List<Category>> getAllCategories(){
    return ResponseEntity.ok(categoryService.getAllCategories());
    
  }
  
  @DeleteMapping(value = "/{id}")
  public ResponseEntity<?> deleteCategory(@PathVariable UUID id) throws Exception {
   
      Book book=bookService.ifBookHasCategory(id);
      if (null != book) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Book exists with this category");
      }
      Category categoryToDelete=categoryService.deleteCategory(id);
      if(categoryToDelete!=null){
        return ResponseEntity.ok(categoryToDelete);
      }else{
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category with id " + id + " not found");
      }
    
  }
  
  
  @PutMapping(value = "/{id}")
  public ResponseEntity<?> updateCategory(@PathVariable UUID id, @RequestBody CategoryDTO categoryDTO) throws Exception {
      boolean categoryExists = categoryService.checkCategoryExists(categoryDTO.getName());
      if (categoryExists) {
        return ResponseEntity.badRequest().body("Category " + categoryDTO.getName()+ " already exists");
      }
      Category updatedCategory = categoryService.updateCategory(id, categoryDTO);
      if(updatedCategory!=null){
        return ResponseEntity.ok(updatedCategory);
      }else{
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category does not exist!");
      }
    
  }
  
}
