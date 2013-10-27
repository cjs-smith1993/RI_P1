package client;

@SuppressWarnings("serial")
/**
 * This class is a simple custom exception for the client. Any error in the client causes this exception to be thrown
 * @author Connor Smith
 *
 */
public class ClientException extends Exception{
	
	public ClientException() {
		return;
	}
	
	public ClientException(String message) {
		super(message);
	}
	
	public ClientException(Throwable throwable) {
		super(throwable);
	}
	
	public ClientException(String message, Throwable throwable) {
		super(message, throwable);
	}
}