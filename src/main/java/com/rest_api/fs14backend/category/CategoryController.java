package com.rest_api.fs14backend.category;

import com.rest_api.fs14backend.book.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/categories")
public class CategoryController {

  @Autowired
  private CategoryRepository repo;
  
  @Autowired
  private CategoryService categoryService;

  @PostMapping("/")
  public Category createOne(@RequestBody Category category) {
    return repo.save(category);
  }
  
  @GetMapping("/")
  public List<Category> getAllCategories(){
    return repo.findAll();
  }
  
  
  
  @DeleteMapping(value = "/{id}")
  public void deleteBook(@PathVariable UUID id) {
    categoryService.deleteCategory(id);
  }
  
  @PutMapping(value = "/{id}")
  public void updateBook(@PathVariable UUID id, @RequestBody Category category) {
    categoryService.updateCategory(id, category);
  }
  
}
