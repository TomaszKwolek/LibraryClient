package pl.spring.demo.rest;

import java.awt.PageAttributes.MediaType;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import pl.spring.demo.constants.ViewNames;
import pl.spring.demo.service.BookService;
import pl.spring.demo.to.BookTo;

@Controller
@ResponseBody
@RequestMapping("/rest")
public class BookRestService {

	@Autowired
	private BookService bookService;

	@RequestMapping(value = "/findAll", method = RequestMethod.GET)
	public ResponseEntity<List<BookTo>> listAllBooks() {
		List<BookTo> allBooks = bookService.findAllBooks();
		if (allBooks.isEmpty()) {
			return new ResponseEntity<List<BookTo>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<BookTo>>(allBooks, HttpStatus.OK);
	}

	@RequestMapping(value = "/findById", method = RequestMethod.GET)
	public ResponseEntity<List<BookTo>> findById(@RequestParam("id") Long id) {
		if (null == id) {
			return new ResponseEntity<List<BookTo>>(HttpStatus.BAD_REQUEST);
		}
		List<BookTo> allBooks = bookService.findBooksById(id);
		if (allBooks.isEmpty()) {
			return new ResponseEntity<List<BookTo>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<BookTo>>(allBooks, HttpStatus.OK);
	}

	@RequestMapping(value = "/findByTitleAndAuthor", method = RequestMethod.GET)
	public ResponseEntity<List<BookTo>> findById(@RequestParam("title") String title,
			@RequestParam("author") String author) {
		List<BookTo> allBooks = bookService.findBookByTitleAndAuthor(title, author);
		if (allBooks.isEmpty()) {
			return new ResponseEntity<List<BookTo>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<BookTo>>(allBooks, HttpStatus.OK);
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ResponseEntity<Void> add(@RequestBody BookTo book) {
		bookService.saveBook(book);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}

	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public ResponseEntity<Long> delete(@RequestParam("id") Long id) {
		if (id == null) {
			return new ResponseEntity<Long>(HttpStatus.BAD_REQUEST);
		}
		bookService.deleteBook(id);
		return new ResponseEntity<Long>(id, HttpStatus.OK);
	}

	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public ResponseEntity<BookTo> update(@RequestBody BookTo book) {
		if (null == bookService.findBooksById(book.getId())) {
			return new ResponseEntity<BookTo>(HttpStatus.BAD_REQUEST);
		}
		List<BookTo> allBooks = bookService.findBooksById(book.getId());
		if (allBooks.isEmpty()) {
			return new ResponseEntity<BookTo>(HttpStatus.NO_CONTENT);
		}

		bookService.saveBook(book);
		return new ResponseEntity<BookTo>(book, HttpStatus.CREATED);
	}

}
