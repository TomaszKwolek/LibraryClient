package pl.spring.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import pl.spring.demo.constants.ViewNames;
import pl.spring.demo.entity.BookEntity;
import pl.spring.demo.service.BookService;
import pl.spring.demo.to.BookTo;

/**
 * Book controller
 * 
 * @author mmotowid
 *
 */
@Controller
@RequestMapping("/books")
public class BookController {
	
	@Autowired
	private BookService bookService;
	
	/**
	 * Method collects info about all books
	 */
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ModelAndView allBooks() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(ViewNames.BOOKS);
		modelAndView.addObject("bookList",bookService.findAllBooks());			
		return modelAndView;
	}

	@RequestMapping(value = "/book", method = RequestMethod.GET)
	public ModelAndView bookInfo(@RequestParam("id") Long id) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(ViewNames.BOOK);
		modelAndView.addObject("book", bookService.findBooksById(id).get(0));		
		return modelAndView;
	}
	
	
	@RequestMapping(value = "/findBook", method = RequestMethod.GET)
	public String findBook(Model model) {
		model.addAttribute("searchedBook", new BookTo());
		return ViewNames.FIND_BOOK;
	}
	
	@RequestMapping(value = "/findById", method = RequestMethod.GET)
	public ModelAndView findById(@ModelAttribute("searchedBook") BookTo searchedBook) {
		ModelAndView modelAndView = new ModelAndView();
		
		modelAndView.setViewName(ViewNames.FOUND_BOOKS);
		modelAndView.addObject("bookList", bookService.findBooksById(searchedBook.getId()));		
		return modelAndView;
	}
	
	@RequestMapping(value = "/foundBook", method = RequestMethod.GET)
	public ModelAndView foundBookInfo(@RequestParam("id") Long id) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(ViewNames.FOUND_BOOK);
		modelAndView.addObject("book", bookService.findBooksById(id).get(0));		
		return modelAndView;
	}
	
	@RequestMapping(value = "/findByTitleAndAuthor", method = RequestMethod.GET)
	public ModelAndView findByTitleOrAuthor(@ModelAttribute("searchedBook") BookTo searchedBook) {
		ModelAndView modelAndView = new ModelAndView();
		
		modelAndView.setViewName(ViewNames.FOUND_BOOKS);
		modelAndView.addObject("bookList", bookService.findBookByTitleAndAuthor(searchedBook.getTitle(), searchedBook.getAuthors()));		
		return modelAndView;
	}
	

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addBook(Model model) {
		model.addAttribute("newBook", new BookTo());
		return ViewNames.ADD_BOOK;
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ModelAndView addBook(@ModelAttribute("newBook") BookTo newBook) {
		ModelAndView modelAndView = new ModelAndView();
		bookService.saveBook(newBook);
		modelAndView.setViewName(ViewNames.ADDED_BOOKS);
		modelAndView.addObject("bookList",bookService.findAllBooks());		
		return modelAndView;
	}
	
	@RequestMapping(value = "/remove", method = RequestMethod.GET)
	public ModelAndView remove(@RequestParam("id") Long id) {
		ModelAndView modelAndView = new ModelAndView();
		bookService.deleteBook(id);
		modelAndView.setViewName(ViewNames.REMOVED);	
		return modelAndView;			
	}


	/**
	 * Binder initialization
	 */
	@InitBinder
	public void initialiseBinder(WebDataBinder binder) {
		binder.setAllowedFields("id", "title", "authors", "status");
	}

}
