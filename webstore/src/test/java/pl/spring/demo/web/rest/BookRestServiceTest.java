package pl.spring.demo.web.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import pl.spring.demo.constants.ViewNames;
import pl.spring.demo.enumerations.BookStatus;
import pl.spring.demo.service.BookService;
import pl.spring.demo.to.BookTo;
import pl.spring.demo.web.utils.FileUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@WebAppConfiguration
public class BookRestServiceTest {

	@Autowired
	private BookService bookService;
	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	@Before
	@Ignore
	public void setUp() {
		Mockito.reset(bookService);
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	@Ignore
	public void testShouldGetAllBooks() throws Exception {

		// given:
		final BookTo bookTo1 = new BookTo(1L, "title", "Author1", BookStatus.FREE);

		// register response for bookService.findAllBooks() mock
		Mockito.when(bookService.findAllBooks()).thenReturn(Arrays.asList(bookTo1));
		// when
		ResultActions response = this.mockMvc.perform(get("/rest/findAll").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content("1"));

		response.andExpect(status().isOk())//
				.andExpect(jsonPath("[0].id").value(bookTo1.getId().intValue()))
				.andExpect(jsonPath("[0].title").value(bookTo1.getTitle()))
				.andExpect(jsonPath("[0].authors").value(bookTo1.getAuthors()));
	}
	
	@Test
	@Ignore
	public void testShouldGetAllBooksNoContent() throws Exception {
		// given:
		List<BookTo> bookToList = new ArrayList<>();

		// register response for bookService.findAllBooks() mock
		Mockito.when(bookService.findAllBooks()).thenReturn(bookToList);
		// when
		ResultActions response = this.mockMvc.perform(get("/rest/findAll").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content("1"));

		response.andExpect(status().isNoContent());
	
	}

	@Test
	@Ignore
	public void testShouldSaveBook() throws Exception {
		// given
		File file = FileUtils.getFileFromClasspath("classpath:pl/spring/demo/web/json/bookToSave.json");
		String json = FileUtils.readFileToString(file);
		// when
		ResultActions response = this.mockMvc.perform(post("/rest/add").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(json.getBytes()));
		// then
		response.andExpect(status().isCreated());
	}

	@Test
	@Ignore
	public void testFindById() throws Exception {
		// given:
		final BookTo bookTo1 = new BookTo(1L, "title", "Author1", BookStatus.FREE);

		// register response for bookService.FindById() mock
		Mockito.when(bookService.findBooksById(Mockito.any())).thenReturn(Arrays.asList(bookTo1));

		// when
		ResultActions response = this.mockMvc.perform(get("/rest/findById").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).param("id", bookTo1.getId().toString()));

		response.andExpect(status().isOk())//
				.andExpect(jsonPath("[0].id").value(bookTo1.getId().intValue()))
				.andExpect(jsonPath("[0].title").value(bookTo1.getTitle()))
				.andExpect(jsonPath("[0].authors").value(bookTo1.getAuthors()));
	}
	

	@Test
	@Ignore
	public void testFindByIdNoContent() throws Exception {
		// given:
		List<BookTo> bookToList = new ArrayList<>();
		Long id =1L;
		// register response for bookService.findAllBooks() mock
		Mockito.when(bookService.findBooksById(Mockito.any())).thenReturn(bookToList);

		// when
		ResultActions response = this.mockMvc.perform(get("/rest/findById").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).param("id", id.toString()));

		response.andExpect(status().isNoContent());
	}
	
	@Test
	@Ignore
	public void testFindByTitleAndAuthor() throws Exception {

		// given:
		final BookTo bookTo1 = new BookTo(1L, "title", "Author1", BookStatus.FREE);

		// register response for bookService.findAllBooks() mock
		Mockito.when(bookService.findBookByTitleAndAuthor(Mockito.any(), Mockito.any()))
				.thenReturn(Arrays.asList(bookTo1));

		// when
		ResultActions response = this.mockMvc
				.perform(get("/rest/findByTitleAndAuthor").accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON).param("title", "t").param("author", "a"));

		response.andExpect(status().isOk())//
				.andExpect(jsonPath("[0].id").value(bookTo1.getId().intValue()))
				.andExpect(jsonPath("[0].title").value(bookTo1.getTitle()))
				.andExpect(jsonPath("[0].authors").value(bookTo1.getAuthors()));
	}
	
	@Test
	@Ignore
	public void testFindByTitleAndAuthorNoContent() throws Exception {

		// given:
		List<BookTo> bookToList = new ArrayList<>();

		// register response for bookService.findAllBooks() mock
		Mockito.when(bookService.findBookByTitleAndAuthor(Mockito.any(), Mockito.any()))
				.thenReturn(bookToList);

		// when
		ResultActions response = this.mockMvc
				.perform(get("/rest/findByTitleAndAuthor").accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON).param("title", "t").param("author", "a"));

		response.andExpect(status().isNoContent());
	}


	@Test
	@Ignore
	public void testRemove() throws Exception {

		// given
		BookTo bookTo1 = new BookTo(1L, "Test title", "Test Author", BookStatus.FREE);
		Mockito.doNothing().when(bookService).deleteBook(Mockito.any());
		// then
		// register response for bookService.findAllBooks() mock
		Mockito.when(bookService.findBookByTitleAndAuthor(Mockito.any(), Mockito.any()))
				.thenReturn(Arrays.asList(bookTo1));

		// when
		ResultActions response = this.mockMvc
				.perform(delete("/rest/delete").accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON).param("id", bookTo1.getId().toString()));

		response.andExpect(status().isOk());

	}
	
	@Test
	@Ignore
	public void testUpdateBook() throws Exception {
		// given
		BookTo bookTo1 = new BookTo(1L, "First Book", "Jan Kowalski", BookStatus.FREE);
		File file = FileUtils.getFileFromClasspath("classpath:pl/spring/demo/web/json/bookToSave.json");
		String json = FileUtils.readFileToString(file);
		// when
		// given:
		Mockito.when(bookService.findBooksById(Mockito.any())).thenReturn(Arrays.asList(bookTo1));

		
		ResultActions response = this.mockMvc.perform(put("/rest/update").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(json.getBytes()));
		// then
		response.andExpect(status().isCreated());
	}
	
	@Test
	@Ignore
	public void testUpdateBookNoContent() throws Exception {
		// given
		List<BookTo> bookToList = new ArrayList<>();
	
		File file = FileUtils.getFileFromClasspath("classpath:pl/spring/demo/web/json/bookToSave.json");
		String json = FileUtils.readFileToString(file);
		// when
		// given:
		Mockito.when(bookService.findBooksById(Mockito.any())).thenReturn(bookToList);

		
		ResultActions response = this.mockMvc.perform(put("/rest/update").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(json.getBytes()));
		// then
		response.andExpect(status().isNoContent());
	}
	
	@Test
	@Ignore
	public void testUpdateBookBadRequest() throws Exception {
		// given
		List<BookTo> bookToList = new ArrayList<>();
	
		File file = FileUtils.getFileFromClasspath("classpath:pl/spring/demo/web/json/bookToSave2.json");
		String json = FileUtils.readFileToString(file);
		// when
		// given:
		Mockito.when(bookService.findBooksById(Mockito.any())).thenReturn(bookToList);

		
		ResultActions response = this.mockMvc.perform(put("/rest/update").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(json.getBytes()));
		// then
		response.andExpect(status().isBadRequest());
	}
}
