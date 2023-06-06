package com.rest_api.fs14backend;

import com.rest_api.fs14backend.SecurityConfig.CustomUserDetailsService;
import com.rest_api.fs14backend.author.AuthorService;
import com.rest_api.fs14backend.book.Book;
import com.rest_api.fs14backend.book.BookController;
import com.rest_api.fs14backend.book.BookMapper;
import com.rest_api.fs14backend.book.BookService;
import com.rest_api.fs14backend.category.CategoryService;
import com.rest_api.fs14backend.utils.JwtUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(BookController.class)
@AutoConfigureMockMvc(addFilters = false)
public class BookControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private BookService bookService;
	
	@MockBean
	private CategoryService categoryService;
	
	@MockBean
	private AuthorService authorService;
	
	@MockBean
	private BookMapper bookMapper;
	
	@MockBean
	private JwtUtils jwtUtils;
	
	@MockBean
	private CustomUserDetailsService customUserDetailsService;
	
	@Test
	public void testGetAllBooks() throws Exception {
		// Create a list of books to be returned by the mocked BookService
		List<Book> books = new ArrayList<>();
		Book book1=new Book();
		book1.setTitle("Book 1");
		books.add(book1);
		Book book2=new Book();
		book2.setTitle("Book 2");
		books.add(book2);
		
		// Mock the behavior of the BookService's getAllBooks method
		when(bookService.getAllBooks()).thenReturn(books);
		
		// Perform a GET request to the "/books" endpoint
		mockMvc.perform(get("/api/v1/books/"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].title", is("Book 3")))
				.andExpect(jsonPath("$[1].title", is("Book 2")));
		
		// Verify that the BookService's getAllBooks method was called
		verify(bookService, times(1)).getAllBooks();
	}
}