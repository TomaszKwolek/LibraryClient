package library.client.javafx.dataprovider.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;

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

	private static final Logger LOG = Logger.getLogger(DataProviderImpl.class);

	private HttpClient httpClient = new HttpClient();
	
	private Collection<BookVO> books = new ArrayList<>();

	public DataProviderImpl() {
		
	}

	@Override
	public Collection<BookVO> findBooks(String title, String author, StateVO state) throws Exception{
		LOG.debug("Entering findbooks()");

		List<BookEntity> entities = new ArrayList<BookEntity>();
		
		entities = httpClient.findBooks(title, author);
		
		books.clear();
		for(BookEntity entity: entities){
			books.add(new BookVO(entity.getId(), StateVO.valueOf(entity.getStatus()), entity.getAuthors(), entity.getTitle()));
		}
		
		Collection<BookVO> result = books.stream().filter(p -> //
		((state == null) || (state != null && p.getState() == state)) //
		).collect(Collectors.toList());

		return result;
	}

	@Override
	public void addBook(String title, String author, String state) throws Exception {

		BookEntity book = new BookEntity(null, title, author, state);
			httpClient.addBook(ParserJSON.toBookJson(book));
	}

	@Override
	public void deleteBook(String id) throws Exception {
			httpClient.remoweBook(id);
	}

}
