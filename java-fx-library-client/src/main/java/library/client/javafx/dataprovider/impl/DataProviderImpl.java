package library.client.javafx.dataprovider.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import library.client.exception.HttpRequestException;
import library.client.javafx.dataprovider.DataProvider;
import library.client.javafx.dataprovider.data.BookVO;
import library.client.javafx.dataprovider.data.StateVO;
import library.client.javafx.http.client.HttpClient;
import library.client.javafx.http.client.ParserJSON;
import library.client.javafx.model.BookEntity;

/**
 * Provides data. Data is stored locally in this object. Additionally a call
 * delay is simulated.
 *
 * @author Tomek
 */
public class DataProviderImpl implements DataProvider {

	private HttpClient httpClient = new HttpClient();
	// REV: nie ma potrzeby przechowywac wynikow jako zmiennej klasy
	private Collection<BookVO> books = new ArrayList<>();

	public DataProviderImpl() {
		
	}

	@Override
	public Collection<BookVO> findBooks(String title, String author, StateVO state) throws HttpRequestException, IOException{
		List<BookEntity> entities = new ArrayList<BookEntity>();
		entities = httpClient.findBooks(title, author);
		books.clear();
		for(BookEntity entity: entities){
			books.add(new BookVO(entity.getId(), StateVO.valueOf(entity.getStatus()), entity.getAuthors(), entity.getTitle()));
		}
		
		Collection<BookVO> result = filterbyState(state);

		return result;
	}

	@Override
	public void addBook(String title, String author, String state) throws  HttpRequestException, IOException {
		BookEntity book = new BookEntity(null, title, author, state);
		httpClient.addBook(ParserJSON.toBookJson(book));
	}

	@Override
	public void deleteBook(String id) throws HttpRequestException, IOException  {
		httpClient.remoweBook(id);
	}
	
	private Collection<BookVO> filterbyState(StateVO state) {
		// REV: filtrowanie powinno byc robione po stronie serwera
		Collection<BookVO> result = books.stream().filter(p -> //
		((state == null) || (state != null && p.getState() == state)) //
		).collect(Collectors.toList());
		return result;
	}

}
