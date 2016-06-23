package library.client.javafx.http.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

import library.client.javafx.model.BookEntity;

public class ParserJSON {
	
	// REV: unikaj static'ow

	public static List<BookEntity> toBookEntity(String json){
		// REV: ten obiekt powinien byc tworzony tylko raz, przy tworzeniu klasy
		Gson g = new Gson();
		BookEntity[] books =  g.fromJson(json, BookEntity[].class);
		List<BookEntity> outputBooks = new ArrayList<BookEntity>();
		for(int i=0; i<books.length; i++){
			outputBooks.add(books[i]);
		}
		return outputBooks;
	}
	
	public static String toBookJson(BookEntity book){
		String jsonBook = "";
		// REV: j.w.
	    Gson gson = new Gson();
	    Type type = new TypeToken<BookEntity>() {}.getType();
	    jsonBook = gson.toJson(book, type);
	    return jsonBook;
	}
	
}
