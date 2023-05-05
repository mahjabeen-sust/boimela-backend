package com.rest_api.fs14backend.book;

import com.rest_api.fs14backend.category.Category;
import com.rest_api.fs14backend.todo.Todo;
import com.rest_api.fs14backend.todo.TodoDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class BookMapper {
	public Book newBook(BookDTO book, Category category){
		return new Book(book.getISBN(),
		book.getTitle(),
		book.getPublishedDate(),
		book.getDescription(),
		book.getStatus(),
		book.getPublishers(),
		category);
	}

}
