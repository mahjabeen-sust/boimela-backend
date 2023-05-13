package com.rest_api.fs14backend.user;

import com.rest_api.fs14backend.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
  User findByUsername(String username);
  
  
  @Query("SELECT s FROM user s WHERE s.id = ?1")
  User findForLoan(UUID id);
}
