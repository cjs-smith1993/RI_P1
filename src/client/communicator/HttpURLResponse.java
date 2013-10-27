package client.communicator;

@Deprecated
public class HttpURLResponse {

	private int responseCode;
	
	public HttpURLResponse NULL_HTTP_URL_RESPONSE = new HttpURLResponse();
	
	private int responseLength;
	
	private Object responseBody;
	
	public boolean equals(Object response) {
		return false;
	}
	
//Get, set
	
	/**
	 * @return the responseCode
	 */
	public int getResponseCode() {
		return responseCode;
	}

	/**
	 * @return the responseLength
	 */
	public int getResponseLength() {
		return responseLength;
	}

	/**
	 * @return the responseBody
	 */
	public Object getResponseBody() {
		return responseBody;
	}
	
	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	public void setResponseLength(int responseLength) {
		this.responseLength = responseLength;
	}
	
	public void setResponseBody(Object responseBody) {
		this.responseBody = responseBody;
	}
}