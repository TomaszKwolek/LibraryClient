package library.client.exception;

// REV: ta klasa jest czescia API HttpClient'a i powinna byc zdefiniowana w jego pakiecie
public class HttpRequestException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public HttpRequestException(String message) {
		super(message);
	}
}
