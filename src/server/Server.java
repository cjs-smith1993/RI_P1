package server;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
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
	}
	
	/**
	 * This method runs the server
	 */
	private static void run(int portNum) {
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
				ValidateUser_Params params = (ValidateUser_Params)requestBody;					
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
				//Validate the user
				results = Database.getInstance().ValidateUser(params);
			}
			else if (command.equals("GetProjects")) {
				GetProjects_Params params = (GetProjects_Params)requestBody;
				ValidateUser_Params validate_params = new ValidateUser_Params(params.getUsername(), params.getPassword());
				exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
				//First validate the user
				results = Database.getInstance().ValidateUser(validate_params);
				//Now get the projects
				if (((ValidateUser_Result) results).getValidity().equals("TRUE")) {
					params = (GetProjects_Params)requestBody;
					results = Database.getInstance().GetProjects((GetProjects_Params)params);
				}
				else
					results = null;
			}
			else if (command.equals("GetSampleImage")) {
				
			}
			else if (command.equals("DownloadBatch")) {

			}
			else if (command.equals("SubmitBatch")) {

			}
			else if (command.equals("GetFields")) {

			}
			else if (command.equals("Search")) {

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
	 * This main routine runs the server
	 * @param args
	 */
	public static void main (String args[]) {
		Server.run(Integer.parseInt(args[0]));
	}
}
