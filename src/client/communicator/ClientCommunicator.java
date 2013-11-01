package client.communicator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import shared.communication.*;
import client.ClientException;

/**
 * This class serves as the communicator between the client and server. When the client receives a request to process a command, it passes the command to the server.
 * @author Connor Smith
 *
 */
public class ClientCommunicator {
//Fields
	/**
	 * The location of the server. In our application, the client and server run on the same machine
	 */
	private static String SERVER_HOST;
	/**
	 * The port number the server is listening to
	 */
	private static int SERVER_PORT;
	/**
	 * The command for a get request
	 */
	private static final String HTTP_GET = "GET";
	/**
	 * The command for a post request
	 */
	private static final String HTTP_POST = "POST";
	/**
	 * The location of the local directory where files will be stored
	 */
	private static final String LOCAL_DIRECTORY = "/Users/CJS/Documents/School/Fall2013/CS_240/workspace/RecordIndexer/local_files";
	/**
	 * An XML stream to allow for easier serialization/deserialization
	 */
	private XStream xmlStream;
	/**
	 * A singleton object for the client communicator
	 */
	private static ClientCommunicator instance = null;
	
//Constructors
	/**
	 * This constructor creates a new XML stream for the communicator
	 */
	private ClientCommunicator() {
		xmlStream = new XStream(new DomDriver());
	}
	
//Getters
	/**
	 * This method returns the current instance of the client communicator or creates one if one doesn't exist
	 * @return The current instance of the client communicator
	 */
	public static ClientCommunicator getInstance(String host, int port) {
		ClientCommunicator.SERVER_HOST = host;
		ClientCommunicator.SERVER_PORT = port;
				
		if (instance == null)
			instance = new ClientCommunicator();
		return instance;
	}
	
	/**
	 * @return the serverHost
	 */
	public static String getServerHost() {
		return SERVER_HOST;
	}

	/**
	 * @return the serverPort
	 */
	public static int getServerPort() {
		return SERVER_PORT;
	}

	/**
	 * @return the urlPrefix
	 */
	public static String getUrlPrefix() {
		return "http://" + getServerHost() + ":" + getServerPort();
	}

	/**
	 * @return the httpGet
	 */
	public static String getHttpGet() {
		return HTTP_GET;
	}

	/**
	 * @return the httpPost
	 */
	public static String getHttpPost() {
		return HTTP_POST;
	}

	/**
	 * @return the xmlStream
	 */
	public XStream getXmlStream() {
		return xmlStream;
	}
	
//Setters
	
//Methods	
	/**
	 * This method sends a get request to the server
	 * @param url The URL to the requested file
	 * @throws ClientException
	 * @throws IOException
	 */
	public void doGet(URL url) throws ClientException, IOException {		
		//Create a connection to the server
		HttpURLConnection connection = (HttpURLConnection)url.openConnection();
		connection.setRequestMethod(HTTP_GET);
		connection.connect();
				
		//If the connection is successful
		if (connection.getResponseCode() == HttpURLConnection.HTTP_OK){
			InputStream responseBody = connection.getInputStream();
			String fileName = url.getPath().substring(url.getPath().lastIndexOf("/")); //Strip out the path and leave only the file name
			
			//Write the desired file to the local directory
			FileOutputStream outputStream = new FileOutputStream(LOCAL_DIRECTORY + fileName);
			int inByte;
			while ((inByte = responseBody.read()) != -1)
				outputStream.write(inByte);
			outputStream.close();
		}
	}
	
	/**
	 * This method sends a post request to the server
	 * @param commandName The name of the command requested
	 * @param postData The encapsulation of the parameters for the command
	 * @return An encapsulation of the results for the command
	 * @throws ClientException
	 */
	public Object doPost(String commandName, Object postData) throws ClientException {
		try {
			//Create a connection to the server
			URL url = new URL(getUrlPrefix() + commandName);
			//System.out.println(url);

			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod(HTTP_POST);
			connection.setDoOutput(true);
			connection.connect();
						
			//Send the data to the server
			xmlStream.toXML(postData, connection.getOutputStream());
			connection.getOutputStream().close();
						
			//If the connection is successful
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				//Get the results from the server
				Object result = xmlStream.fromXML(connection.getInputStream());
				return result;
			}
			else {
				throw new ClientException(String.format("doPost failed %s (http code %d)", commandName, connection.getResponseCode()));
			}
		}
		catch (IOException e) {
			throw new ClientException(String.format("doPost failed: %s", e.getMessage()), e);
		}
	}

	/**
	 * This method validates the user's credentials
	 * @param params an encapsulation of the user's credentials
	 * @return an encapsulation of the server's response
	 * @throws ClientException 
	 */
	public ValidateUser_Result ValidateUser(ValidateUser_Params params) throws ClientException {
		return (ValidateUser_Result)doPost("/ValidateUser", params);
	}
	
	/**
	 * This method gets information about all of the available projects
	 * @param params an encapsulation of the user's credentials
	 * @return a list of information for each project
	 * @throws ClientException 
	 */
	public GetProjects_Result GetProjects(GetProjects_Params params) throws ClientException {
		return (GetProjects_Result)doPost("/GetProjects", params);
	}
	
	/**
	 * This method gets a sample image from a specified project
	 * @param params an encapsulation of the user's credentials and a project id
	 * @return a url to a sample image
	 * @throws ClientException 
	 */
	public GetSampleImage_Result GetSampleImage(GetSampleImage_Params params) throws ClientException {
		return (GetSampleImage_Result)doPost("/GetSampleImage", params);
	}
	
	/**
	 * This method downloads a batch for the user
	 * @param params an encapsulation of the user's credentials and a project id
	 * @return a complete batch from the specified project
	 * @throws ClientException 
	 */
	public DownloadBatch_Result DownloadBatch(DownloadBatch_Params params) throws ClientException {
		return (DownloadBatch_Result)doPost("/DownloadBatch", params);
	}
	
	/**
	 * This method submits an indexed batch to the server
	 * @param params an encapsulation of the user's credentials, a batch id, and a list of field values
	 * @return success or failure based on the server's response
	 * @throws ClientException 
	 */
	public SubmitBatch_Result SubmitBatch(SubmitBatch_Params params) throws ClientException {
		return (SubmitBatch_Result)doPost("/SubmitBatch", params);
	}
	
	/**
	 * This method returns information about all of the fields for a specified project or all projects
	 * @param params an encapsulation of the user's credentials and a project id. If no project id is given, information about all of the fields in the database is returned.
	 * @return a list of information about each field
	 * @throws ClientException 
	 */
	public GetFields_Result GetFields(GetFields_Params params) throws ClientException {
		return (GetFields_Result)doPost("/GetFields", params);
	}
	
	/**
	 * This method searches the indexed records for search terms
	 * @param params an encapsulation of the user's credentials, search fields, and search values
	 * @return a list of results matching the search criteria
	 * @throws ClientException 
	 */
	public Search_Result Search(Search_Params params) throws ClientException {
		return (Search_Result)doPost("/Search", params);
	}
	
	/**
	 * This method downloads a batch from the given URL
	 * @param path a URL for the image
	 * @return an image file corresponding to the URL
	 */
	public File DownloadFile(String path) {
		try {
			//Download the file at the desired URL
			URL url = new URL(path);
			doGet(url);
		}
		catch (ClientException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}

//Main
	/**
	 * This main routine creates an instance of the client communicator and starts the client side of the program
	 * @param args
	 */
	public static void main(String args[]) {
	}
}
