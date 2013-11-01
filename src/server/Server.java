package server;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import server.database.Database;
import shared.communication.*;
import com.sun.net.httpserver.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * This class is the server for the project. It handles all requests from the client and communicates with the database.
 * @author Connor Smith
 *
 */
public class Server {
//Fields
	/**
	 * The maximum number of waiting connections allowed
	 */
	private static final int MAX_WAITING_CONNECTIONS = 10;
	
	/**
	 * The instance of the server
	 */
	private static HttpServer server = null;
	/**
	 * An instance of the database
	 */
	private static Database database = null;
	/**
	 * An XML stream to allow for easier serialization
	 */
	private static XStream xmlStream = new XStream(new DomDriver());
		
//Constructors
	private Server() {
		super();
	}
	
//Getters
	/**
	 * This method returns the current instance of the server, or creates and initializes it if one doesn't exist
	 * @return The current instance of the server
	 */
	public static HttpServer getInstance(int portNum) {
		if (server == null) {
			try {
				//Create the server if it doesn't exist
				server = HttpServer.create(new InetSocketAddress(portNum), MAX_WAITING_CONNECTIONS);
				server.setExecutor(null);
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		return server;
	}
	
	/**
	 * This method returns the instance of the database
	 * @return The instance of the database
	 */
	public static Database getDatabase() {
		return database;
	}
	
//Setters
	
//Methods
	/**
	 * This method sets up all required parts of the server
	 */
	private static void initialize() {
		//Create the database if it doesn't exist
		database = Database.getInstance();
		
		//Set up contexts and handlers for requests
		server.createContext("/ValidateUser", validateUserHandler);
		server.createContext("/GetProjects", getProjectsHandler);
		server.createContext("/GetSampleImage", getSampleImageHandler);
		server.createContext("/DownloadBatch", downloadBatchHandler);
		server.createContext("/SubmitBatch", submitBatchHandler);
		server.createContext("/GetFields", getFieldsHandler);
		server.createContext("/Search", searchHandler);
		server.createContext("/", downloadHandler);
	}
	
	/**
	 * This method runs the server
	 */
	public static void run(int portNum) {
		server = Server.getInstance(portNum);
		Server.initialize();
		server.start();
	}
	
	/**
	 * This method handles all of the server commands
	 * @param exchange An HttpExchange object
	 * @param command The name of the command
	 */
	private static void handleCommand(HttpExchange exchange, String command) {		
		//Get the input parameters from the client
		Object requestBody = xmlStream.fromXML(exchange.getRequestBody());
				
		//For the appropriate command, perform the command and send the result back to the client
		try {
			//Start the transaction
			Database.getInstance().startTransaction();
			Object results = null;
			
			if (command.equals("ValidateUser")) {
				//Validate the user
				results = validate(exchange, (ValidateUser_Params)requestBody);
			}
			else if (command.equals("GetProjects")) {
				GetProjects_Params params = (GetProjects_Params)requestBody;
				//First validate the user
				results = validate(exchange, new ValidateUser_Params(params.getUsername(), params.getPassword()));
				if (((ValidateUser_Result) results).isValid()) {
					//Now get the projects
					results = Database.getInstance().GetProjects(params);
				}
				else
					results = new GetProjects_Result(null);
			}
			else if (command.equals("GetSampleImage")) {
				GetSampleImage_Params params = (GetSampleImage_Params)requestBody;
				//First validate the user
				results = validate(exchange, new ValidateUser_Params(params.getUsername(), params.getPassword()));
				if (((ValidateUser_Result) results).isValid()) {
					//Now get the sample image
					results = Database.getInstance().GetSampleImage(params);
					String url = ((GetSampleImage_Result)results).getImage_url();
					((GetSampleImage_Result)results).setImage_url(InetAddress.getLocalHost().getHostName()+url);
				}
				else
					results = new GetSampleImage_Result(null);
			}
			else if (command.equals("DownloadBatch")) {
				DownloadBatch_Params params = (DownloadBatch_Params)requestBody;
				//First validate the user
				results = validate(exchange, new ValidateUser_Params(params.getUsername(), params.getPassword()));
				if (((ValidateUser_Result) results).isValid()) {
					//Now download the batch
					results = Database.getInstance().DownloadBatch(params);
				}
				else
					results = new DownloadBatch_Result(-1, -1, "", -1, -1, -1, -1, null);
			}
			else if (command.equals("SubmitBatch")) {
				SubmitBatch_Params params = (SubmitBatch_Params)requestBody;
				//First validate the user
				results = validate(exchange, new ValidateUser_Params(params.getUsername(), params.getPassword()));
				if (((ValidateUser_Result)results).isValid()) {
					//Now submit the batch
					results = Database.getInstance().SubmitBatch(params);
				}
				else
					results = new SubmitBatch_Result(false);
			}
			else if (command.equals("GetFields")) {
				GetFields_Params params = (GetFields_Params)requestBody;
				//First validate the user
				results = validate(exchange, new ValidateUser_Params(params.getUsername(), params.getPassword()));
				if (((ValidateUser_Result)results).isValid()) {
					//Now get the fields
					results = Database.getInstance().GetFields(params);
				}
				else
					results = new GetFields_Result(null);
			}
			else if (command.equals("Search")) {
				Search_Params params = (Search_Params)requestBody;
				//First validate the user
				results = validate(exchange, new ValidateUser_Params(params.getUsername(), params.getPassword()));
				if (((ValidateUser_Result)results).isValid()) {
					//Now search
					results = Database.getInstance().Search(params);
				}
				else
					results = new Search_Result(null);
			}

			//Send the results back to the client and end the transaction
			xmlStream.toXML(results, exchange.getResponseBody());
			exchange.close();
			Database.getInstance().endTransaction(true);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method returns a ValidateUser_Result containing the result of the validation request
	 * @param exchange An HttpExchange object
	 * @param params An encapsulation of a user's username and password
	 * @return An encapsulation of the user with the matching username and password, or an empty ValidateUser_Result if the validation fails
	 * @throws IOException
	 */
	private static ValidateUser_Result validate(HttpExchange exchange, ValidateUser_Params params) throws IOException {
		exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
		return Database.getInstance().ValidateUser(params);
	}
	
	/**
	 * This anonymous inner class handles the Validate User request
	 */
	private static HttpHandler validateUserHandler = new HttpHandler() {
		
		@Override
		public void handle(HttpExchange exchange) throws IOException {
			handleCommand(exchange, "ValidateUser");
		}
	};
	
	/**
	 * This anonymous inner class handles the Get Projects request
	 */
	private static HttpHandler getProjectsHandler = new HttpHandler() {
		
		@Override
		public void handle(HttpExchange exchange) throws IOException {
			handleCommand(exchange, "GetProjects");
		}
	};
	
	/**
	 * This anonymous inner class handles the Get Sample Image request
	 */
	private static HttpHandler getSampleImageHandler = new HttpHandler() {
		
		@Override
		public void handle(HttpExchange exchange) throws IOException {
			handleCommand(exchange, "GetSampleImage");
		}
	};
	
	/**
	 * This anonymous inner class handles the Download Batch request
	 */
	private static HttpHandler downloadBatchHandler = new HttpHandler() {
		
		@Override
		public void handle(HttpExchange exchange) throws IOException {
			handleCommand(exchange, "DownloadBatch");
		}
	};
	
	/**
	 * This anonymous inner class handles the Submit Batch request
	 */
	private static HttpHandler submitBatchHandler = new HttpHandler() {
		
		@Override
		public void handle(HttpExchange exchange) throws IOException {
			handleCommand(exchange, "SubmitBatch");
		}
	};
	
	/**
	 * This anonymous inner class handles the Get Fields request
	 */
	private static HttpHandler getFieldsHandler = new HttpHandler() {
		
		@Override
		public void handle(HttpExchange exchange) throws IOException {
			handleCommand(exchange, "GetFields");
		}
	};

	/**
	 * This anonymous inner class handles the Search request
	 */
	private static HttpHandler searchHandler = new HttpHandler() {
		
		@Override
		public void handle(HttpExchange exchange) throws IOException {
			handleCommand(exchange, "Search");
		}
	};
	
	/**
	 * This anonymous inner class handles a file download request
	 */
	private static HttpHandler downloadHandler = new HttpHandler() {
		
		@Override
		public void handle(HttpExchange exchange) throws IOException {
			String file_path = exchange.getRequestURI().toString();
			Path path = Paths.get(file_path);
			byte[] bytes = Files.readAllBytes(path);
						
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, bytes.length);
			OutputStream os = exchange.getResponseBody();
			os.write(bytes);
			os.close();
		}
	};
	
	/**
	 * This main routine runs the server
	 * @param args
	 */
	public static void main (String args[]) {
		Server.run(Integer.parseInt(args[0]));
	}
}
