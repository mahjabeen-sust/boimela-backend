package com.rest_api.fs14backend.book;

import com.rest_api.fs14backend.author.Author;
import com.rest_api.fs14backend.category.Category;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "book")
@Data
@NoArgsConstructor
public class Book {

  @Id
  @Column(name = "id")
  @SequenceGenerator(
          name = "book_sequence",
          sequenceName = "book_sequence",
          allocationSize = 1
  )
  @GeneratedValue(
          strategy = GenerationType.SEQUENCE,
          generator = "book_sequence"
  )
  private long id;

  @Column( unique = true)
  private long ISBN;

  @Column(nullable = true)
  private String title;

  @Column(nullable = true)
  private LocalDate publishedDate;

  @Column(nullable = true)
  private String description;

  @Enumerated(EnumType.STRING)
  private Status status;

  @Column(nullable = true)
  private String publishers;

//  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
//  @ManyToOne(optional = false)
//  private  author;

  public enum Status {
    BORROWED,
    AVAILABLE
  }
  
  @ManyToOne( optional = false)
  @JoinColumn(name = "category_id")
  private Category category;

  @ManyToMany
  @JoinColumn(name = "author_id")
  private List<Author> authorList;
  
  public Book(long ISBN,
              String title,
              LocalDate publishedDate,
              String description,
              Status status,
              String publishers,
              Category category,
              List<Author> authorList

//              Author author
  ) {
    this.ISBN = ISBN;
    this.title = title;
    this.publishedDate = publishedDate;
    this.description = description;
    this.status = status;
    this.publishers = publishers;
    this.category = category;
    this.authorList = authorList;
  
  }


  public Book (long ISBN, String title) {
    this.ISBN = ISBN;
    this.title = title;
  }


//  public Category getCategory() {
//    return category;
//  }

//  public void setCategory(Category category) {
//    this.category = category;
//  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Book book = (Book) o;
    return ISBN == book.ISBN && Objects.equals(title, book.title) && Objects.equals(publishedDate, book.publishedDate) && Objects.equals(description, book.description) && status == book.status && Objects.equals(publishers, book.publishers);
  }

  @Override
  public int hashCode() {
    return Objects.hash(ISBN, title, publishedDate, description, status, publishers);
  }

  @Override
  public String toString() {
    return "Book{" +
            "ISBN=" + ISBN +
            ", title='" + title + '\'' +
            ", publishedDate=" + publishedDate +
            ", description='" + description + '\'' +
            ", status=" + status +
            ", publishers='" + publishers + '\'' +
            '}';
  }
}


