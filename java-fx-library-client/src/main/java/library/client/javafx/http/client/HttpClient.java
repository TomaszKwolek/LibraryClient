package library.client.javafx.http.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import library.client.exception.HttpRequestException;
import library.client.javafx.model.BookEntity;

public class HttpClient {

    
 // HTTP GET request
    public List<BookEntity> findBooks(String title, String author) throws HttpRequestException, IOException {
    	
    	String param1 = ((title == null) ? "" : title);
    	String param2 = ((author == null) ? "" : author);
    	
        URL url = new URL("http://localhost:8080/webstore/rest/findByTitleAndAuthor?title="+param1+"&author="+param2);

        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        con.setRequestMethod("GET");
        
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        
        if (con.getResponseCode() != HttpURLConnection.HTTP_OK && con.getResponseCode() != HttpURLConnection.HTTP_NO_CONTENT) {
            throw new HttpRequestException("Failed : HTTP error code : " + con.getResponseCode());
        }
        
        String inputLine;
        List<BookEntity> books = new ArrayList<BookEntity>();
        while ((inputLine = in.readLine()) != null) {
        	 books = ParserJSON.toBookEntity(inputLine.toString());
        }
        
        in.close();
        con.disconnect();

        return books;
    }
    
 // HTTP POST request
    public void addBook(String json) throws HttpRequestException, IOException {
  
        URL url = new URL("http://localhost:8080/webstore/rest/add");

        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setDoOutput(true);
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");

        OutputStream os = con.getOutputStream();
        os.write(json.getBytes());
        os.flush();
        
        if (con.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
            throw new HttpRequestException("Failed : HTTP error code : "
                + con.getResponseCode());
        }
        
        os.close();
        con.disconnect();

    }
    
 // HTTP DELET request
    public void remoweBook(String id) throws HttpRequestException, IOException {
  
    	String param1 = ((id == null) ? "" : id);
 
        URL url = new URL("http://localhost:8080/webstore/rest/delete?id="+param1);

        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        con.setRequestMethod("DELETE");
        con.connect();
         
        if (con.getResponseCode() != HttpURLConnection.HTTP_OK) {
            throw new HttpRequestException("Failed : HTTP error code : "
                + con.getResponseCode());
        }

        con.disconnect();
    }
	
}
