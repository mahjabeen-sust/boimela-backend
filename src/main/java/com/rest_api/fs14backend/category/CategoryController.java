package com.rest_api.fs14backend.category;

import com.rest_api.fs14backend.book.Book;
import org.springframework.beans.factory.annotation.Autowired;
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

  @PostMapping("/")
  public ResponseEntity<?> createOne(@RequestBody CategoryDTO categoryDTO) throws Exception {
    Category category=new Category();
    category.setName(categoryDTO.getName());
    return categoryService.createOne(category);
    //return repo.save(category);
  }
  
  @GetMapping("/")
  public List<Category> getAllCategories(){
    return categoryService.getAllCategories();
  }
  
  
  
  @DeleteMapping(value = "/{id}")
  public ResponseEntity<?> deleteCategory(@PathVariable UUID id) throws Exception {
    return categoryService.deleteCategory(id);
  }
  
  @PutMapping(value = "/{id}")
  public ResponseEntity<?> updateCategory(@PathVariable UUID id, @RequestBody CategoryDTO categoryDTO) throws Exception {
    return categoryService.updateCategory(id, categoryDTO);
  }
  
}
