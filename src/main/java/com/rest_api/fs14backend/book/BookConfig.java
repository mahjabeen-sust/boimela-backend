//package com.rest_api.fs14backend.book;
//
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.List;
//
//@Configuration
//public class BookConfig {
//  @Bean
//  CommandLineRunner commandLineRunner(BookRepository repository) {
//    return args -> {
//      Book book2 = new Book(
//              123433, "Book2", null, "This is Book2",
//              Book.Status.AVAILABLE, "Publisher2"
//      );
//      Book book3 = new Book(
//              123432, "Book3", null, "This is Book3",
//              Book.Status.AVAILABLE, "Publisher3"
//      );
//      Book book4 = new Book(
//              123431, "Book4", null, "This is Book4",
//              Book.Status.BORROWED, "Publisher4"
//      );
//      Book book5 = new Book(
//              123430, "Book5", null, "This is Book5",
//              Book.Status.AVAILABLE, "Publisher5"
//      );
//      repository.saveAll(
//              List.of(book2, book3, book4, book5)
//      );
//    };
//  }
//}
//
//
