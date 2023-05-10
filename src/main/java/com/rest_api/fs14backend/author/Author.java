package com.rest_api.fs14backend.author;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.util.UUID;


@Entity
@Table(name = "author")
@Data
@NoArgsConstructor
public class Author {
  @Id
  @GeneratedValue
  @UuidGenerator
  private UUID id;

  @Column(nullable = false, unique = true)
  private String name;
  @Column(nullable = true)
  private LocalDate dob;
  //  @Column(nullable = true)
//  private ArrayList<Book> books;
  @Column(nullable = true)
  private String description;

  public String getDescription() {
    return description;
  }

  public Author( String name, LocalDate dob, String description) {
    this.name = name;
    this.dob = dob;
//    this.books = books;
    this.description = description;
  }

  public Author(String name, String description) {
    this.name = name;
    this.description = description;
  }
}

