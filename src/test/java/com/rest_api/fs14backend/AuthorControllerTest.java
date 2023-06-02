package com.rest_api.fs14backend;

import com.rest_api.fs14backend.SecurityConfig.CustomUserDetailsService;
import com.rest_api.fs14backend.author.Author;
import com.rest_api.fs14backend.author.AuthorController;
import com.rest_api.fs14backend.author.AuthorService;
import com.rest_api.fs14backend.book.Book;
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
@WebMvcTest(AuthorController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AuthorControllerTest {
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private AuthorService authorService;
	
	@MockBean
	private JwtUtils jwtUtils;
	
	@MockBean
	private CustomUserDetailsService customUserDetailsService;
	
	@Test
	public void testGetAuthors() throws Exception {
		// Create a list of books to be returned by the mocked BookService
		List<Author> authors = new ArrayList<>();
		Author author1=new Author();
		author1.setName("Author 1");
		authors.add(author1);
		Author author2=new Author();
		author2.setName("Author 2");
		authors.add(author2);
		
		// Mock the behavior of the BookService's getAllBooks method
		when(authorService.getAllAuthors()).thenReturn(authors);
		
		// Perform a GET request to the "/books" endpoint
		mockMvc.perform(get("/api/v1/authors/"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].name", is("Author 1")))
				.andExpect(jsonPath("$[1].name", is("Author 2")));
		
		// Verify that the BookService's getAllBooks method was called
		verify(authorService, times(1)).getAllAuthors();
	}
	
}
