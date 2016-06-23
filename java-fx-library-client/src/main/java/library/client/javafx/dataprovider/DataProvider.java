package library.client.javafx.dataprovider;


import java.io.IOException;
import java.util.Collection;

import library.client.exception.HttpRequestException;
import library.client.javafx.dataprovider.data.BookVO;
import library.client.javafx.dataprovider.data.StateVO;
import library.client.javafx.dataprovider.impl.DataProviderImpl;


/**
 * Provides data.
 *
 * @author Tomek
 */
public interface DataProvider {


	DataProvider INSTANCE = new DataProviderImpl();

	// REV: implementacja rzuca tylko HttpRequestException i IOException, te wyjatki powinny byc w interfejsie
	Collection<BookVO> findBooks(String title, String author, StateVO state) throws Exception;
	void addBook(String title, String author, String state) throws Exception;
	void deleteBook(String id) throws Exception;

}
