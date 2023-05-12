package com.rest_api.fs14backend.book;

import com.rest_api.fs14backend.author.Author;
import com.rest_api.fs14backend.category.Category;
import com.rest_api.fs14backend.todo.Todo;
import com.rest_api.fs14backend.todo.TodoDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class BookMapper {
	public Book newBook(BookDTO book, Category category, List<Author> authorList){
		return new Book(book.getISBN(),
		book.getTitle(),
		book.getPublishedDate(),
		book.getDescription(),
		book.getStatus(),
		book.getPublishers(),
		category,
				authorList);
	}

}
