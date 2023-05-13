package com.rest_api.fs14backend.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

  @Query("SELECT s FROM Book s WHERE s.ISBN = ?1")
  Optional<Book> findBookByISBN(Long isbn);
  
  @Query("SELECT s FROM Book s WHERE s.ISBN = ?1")
  Book findForLoan(Long isbn);
}
