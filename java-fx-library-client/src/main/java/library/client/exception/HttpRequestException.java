package library.client.exception;

public class HttpRequestException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public HttpRequestException(String message) {
		super(message);
	}
}
