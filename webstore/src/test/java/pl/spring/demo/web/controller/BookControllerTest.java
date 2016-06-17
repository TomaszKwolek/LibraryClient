package pl.spring.demo.web.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.fasterxml.jackson.databind.ObjectMapper;

import pl.spring.demo.constants.ViewNames;
import pl.spring.demo.controller.BookController;
import pl.spring.demo.enumerations.BookStatus;
import pl.spring.demo.service.BookService;
import pl.spring.demo.to.BookTo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "controller-test-configuration.xml")
@WebAppConfiguration
public class BookControllerTest {

	@Autowired
	private BookService bookService;

	private MockMvc mockMvc;

	@Before
	@Ignore
	public void setup() {
		Mockito.reset(bookService);

		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");

		BookController bookController = new BookController();
		mockMvc = MockMvcBuilders.standaloneSetup(bookController).setViewResolvers(viewResolver).build();
		// Due to fact, that We are trying to construct real Bean - Book
		// Controller, we have to use reflection to mock existing field book
		// service
		ReflectionTestUtils.setField(bookController, "bookService", bookService);
	}

	@Test
	@Ignore
	public void testAddBookPage() throws Exception {
		// given
		BookTo testBook = new BookTo(1L, "Test title", "Test Author", BookStatus.FREE);
		Mockito.when(bookService.saveBook(Mockito.any())).thenReturn(testBook);
		ResultActions resultActions = mockMvc.perform(post("/books/add").flashAttr("newBook", testBook));
		// then
		resultActions.andExpect(view().name(ViewNames.ADDED_BOOKS))
				.andExpect(model().attribute("newBook", new ArgumentMatcher<Object>() {
					@Override
					public boolean matches(Object argument) {
						BookTo book = (BookTo) argument;
						return null != book && testBook.getTitle().equals(book.getTitle());
					}
				}));

	}

	@Test
	@Ignore
	public void testFindAllPage() throws Exception {
		// given
		List<BookTo> testBookList = new ArrayList<BookTo>();
		testBookList.add(new BookTo(1L, "Test title 1", "Test Author 1", BookStatus.FREE));
		testBookList.add(new BookTo(2L, "Test title 2", "Test Author 2", BookStatus.FREE));
		testBookList.add(new BookTo(3L, "Test title 3", "Test Author 3", BookStatus.FREE));
		
		Mockito.when(bookService.findAllBooks()).thenReturn(testBookList);
		ResultActions resultActions = mockMvc.perform(get("/books/all"));
		// then
		resultActions.andExpect(view().name(ViewNames.BOOKS))
				.andExpect(model().attribute("bookList", new ArgumentMatcher<Object>() {
					@Override
					public boolean matches(Object argument) {
						@SuppressWarnings("unchecked")
						List<BookTo> bookList = (List<BookTo>) argument;
						return null != bookList && testBookList.equals(bookList);
					}
				}));
	}

	@Test
	@Ignore
	public void testBookDetails() throws Exception {
		// given
		BookTo testBook = new BookTo(1L, "Test title", "Test Author", BookStatus.FREE);
		Mockito.when(bookService.findBooksById((Mockito.any()))).thenReturn(Arrays.asList(testBook));

		ResultActions resultActions = mockMvc.perform(get("/books/book").param("id", testBook.getId().toString()));
		// then
		resultActions.andExpect(view().name(ViewNames.BOOK))
				.andExpect(model().attribute("book", new ArgumentMatcher<Object>() {
					@Override
					public boolean matches(Object argument) {
						BookTo book = (BookTo) argument;
						return null != book && testBook.getTitle().equals(book.getTitle())
											&& testBook.getAuthors().equals(book.getAuthors());
					}
				}));

	}

	@Test
	@Ignore
	public void testFoundBook() throws Exception {
		// given
		BookTo testBook = new BookTo(1L, "Test title", "Test Author", BookStatus.FREE);
		Mockito.when(bookService.findBooksById((Mockito.any()))).thenReturn(Arrays.asList(testBook));

		ResultActions resultActions = mockMvc.perform(get("/books/foundBook").param("id", testBook.getId().toString()));
		// then
		resultActions.andExpect(view().name(ViewNames.FOUND_BOOK))
				.andExpect(model().attribute("book", new ArgumentMatcher<Object>() {
					@Override
					public boolean matches(Object argument) {
						BookTo book = (BookTo) argument;
						return null != book && testBook.getTitle().equals(book.getTitle())
											&& testBook.getAuthors().equals(book.getAuthors());
					}
				}));

	}

	@Test
	@Ignore
	public void testFindBook() throws Exception {
		// given
		ResultActions resultActions = mockMvc.perform(get("/books/findBook"));
		// then
		resultActions.andExpect(view().name(ViewNames.FIND_BOOK));

	}

	@Test
	@Ignore
	public void testFindById() throws Exception {
		// given
		BookTo searchedBook = new BookTo(1L, "xxx", "yyy", BookStatus.FREE);
		BookTo foundBook = new BookTo(1L, "Test title", "Test Author", BookStatus.FREE);
		Mockito.when(bookService.findBooksById(Mockito.any())).thenReturn(Arrays.asList(foundBook));

		ResultActions resultActions = mockMvc.perform(get("/books/findById").flashAttr("searchedBook", searchedBook));
		// then
		resultActions.andExpect(view().name(ViewNames.FOUND_BOOKS))
				.andExpect(model().attribute("bookList", new ArgumentMatcher<Object>() {
					@Override
					@SuppressWarnings("unchecked")
					public boolean matches(Object argument) {
						List<BookTo> bookList = (List<BookTo>) argument;
						return null != bookList	&& Arrays.asList(searchedBook).get(0).getId().equals(bookList.get(0).getId())
												&& foundBook.getTitle().equals(bookList.get(0).getTitle())
												&& foundBook.getAuthors().equals(bookList.get(0).getAuthors());
					}
				}));

	}

	@Test
	@Ignore
	public void testfindByTitleAndAuthor() throws Exception {
		// given
		BookTo searchedBook = new BookTo(1L, "es", "Auth", BookStatus.FREE);
		BookTo foundBook = new BookTo(1L, "Test title", "Test Author", BookStatus.FREE);
		Mockito.when(bookService.findBookByTitleAndAuthor(Mockito.any(), Mockito.any()))
				.thenReturn(Arrays.asList(foundBook));

		ResultActions resultActions = mockMvc
				.perform(get("/books/findByTitleAndAuthor").flashAttr("searchedBook", searchedBook));
		// then
		resultActions.andExpect(view().name(ViewNames.FOUND_BOOKS))
				.andExpect(model().attribute("bookList", new ArgumentMatcher<Object>() {
					@Override
					@SuppressWarnings("unchecked")
					public boolean matches(Object argument) {
						List<BookTo> bookList = (List<BookTo>) argument;
						return null != bookList && bookList.get(0).getTitle().contains(searchedBook.getTitle())
												&& bookList.get(0).getAuthors().contains(searchedBook.getAuthors());
					}
				}));

	}

	@Test
	@Ignore
	public void testRemove() throws Exception {
		// given
		BookTo testBook = new BookTo(1L, "Test title", "Test Author", BookStatus.FREE);
		Mockito.doNothing().when(bookService).deleteBook(Mockito.any());
		ResultActions resultActions = mockMvc.perform(get("/books/remove").param("id", testBook.getId().toString()));
		// then
		resultActions.andExpect(view().name(ViewNames.REMOVED));
	
	}

	/**
	 * Sample method which convert's any object from Java to String
	 */
	private static String asJsonString(final Object obj) {
		try {
			final ObjectMapper mapper = new ObjectMapper();
			final String jsonContent = mapper.writeValueAsString(obj);
			return jsonContent;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
