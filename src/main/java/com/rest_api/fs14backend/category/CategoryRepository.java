package com.rest_api.fs14backend.category;

import com.rest_api.fs14backend.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {
	
	boolean existsByName(String categoryName);
	@Query("SELECT s FROM category s WHERE s.id = ?1")
	Optional<Category> findCategoryById(UUID id);
}
