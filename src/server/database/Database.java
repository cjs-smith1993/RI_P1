package server.database;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import shared.communication.*;

public class Database {
//Fields
	/**
	 * A singleton object for the database
	 */
	private static Database instance = null;
	/**
	 * A representation of the users table in the database
	 */
	private Users users;
	/**
	 * A representation of the projects table in the database
	 */
	private Projects projects;
	/**
	 * A representation of the fields table in the database
	 */
	private Fields fields;
	/**
	 * A representation of the batches table in the database
	 */
	private Batches batches;
	/**
	 * A representation of the records table in the database
	 */
	private Values values;
	/**
	 * A connection to the SQL database
	 */
	private static Connection connection = null;
	/**
	 * The name of the database file
	 */
	private static String DATABASE_FILE = "indexer_server.sqlite";
	/**
	 * The name of the database
	 */
	private static String DATABASE_NAME = "database" + File.separator + DATABASE_FILE;
	/**
	 * The full url to the database
	 */
	private static String CONNECTION_URL = "jdbc:sqlite:" + DATABASE_NAME;
	
//Constructors
	/**
	 * This constructor initializes the database
	 */
	private Database() {
		users = new Users();
		projects = new Projects();
		fields = new Fields();
		batches = new Batches();
		values = new Values();
	}

//Getters
	/**
	 * This method returns the instance of the database, or creates a new one if one doesn't exist
	 * @return The instance of the database
	 */
	public static Database getInstance() {
		if (instance == null) {
			instance = new Database();
		}
		return instance;
	}
	
	/**
	 * This methods returns the users table
	 * @return the users table
	 */
	public Users getAllUsers() {
		return users;
	}

	/**
	 * This methods returns the projects table
	 * @return the projects table
	 */
	public Projects getAllProjects() {
		return projects;
	}

	/**
	 * This methods returns the fields table
	 * @return the fields table
	 */
	public Fields getAllFields() {
		return fields;
	}

	/**
	 * This methods returns the batches table
	 * @return the batches table
	 */
	public Batches getAllBatches() {
		return batches;
	}

	/**
	 * This methods returns the records table
	 * @return the records table
	 */
	public Values getRecords() {
		return values;
	}
	
	/**
	 * This method returns the SQL connection to the database
	 * @return the SQL connection to the database
	 */
	public static Connection getConnection() {
		return connection;
	}

//Setters

//Methods	
	/**
	 * This method opens a connection to the SQL database
	 */
	public void startTransaction() {
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection(CONNECTION_URL);
			connection.setAutoCommit(false);
		}
		catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method closes the connection to the SQL database
	 * @param commit True if the database operation was successful, otherwise false
	 */
	public void endTransaction(boolean commit) {
		try {
			//If the transaction was successful, commit the changes. Otherwise, revert back
			if (commit)
				connection.commit();
			else
				connection.rollback();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		//Try to close the connection
		finally {
			try {
				connection.close();
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
//Commands
	/**
	 * This method validates the user's credentials
	 * @param params an encapsulation of the user's credentials
	 * @return an encapsulation of the server's response
	 */
	public ValidateUser_Result ValidateUser(ValidateUser_Params params) {
		return users.ValidateUser(params);
	}
	
	/**
	 * This method gets information about all of the available projects
	 * @param params an encapsulation of the user's credentials
	 * @return a list of information for each project
	 */
	public GetProjects_Result GetProjects(GetProjects_Params params) {
		return null;
	}
	
	/**
	 * This method gets a sample image from a specified project
	 * @param params an encapsulation of the user's credentials and a project id
	 * @return a url to a sample image
	 */
	public GetSampleImage_Result GetSampleImage(GetSampleImage_Params params) {
		return null;
	}
	
	/**
	 * This method downloads a batch for the user
	 * @param params an encapsulation of the user's credentials and a project id
	 * @return a complete batch from the specified project
	 */
	public DownloadBatch_Result DownloadBatch(DownloadBatch_Params params) {
		return null;
	}
	
	/**
	 * This method submits an indexed batch to the server
	 * @param params an encapsulation of the user's credentials, a batch id, and a list of field values
	 * @return success or failure based on the server's response
	 */
	public SubmitBatch_Result SubmitBatch(SubmitBatch_Params params) {
		return null;
	}
	
	/**
	 * This method returns information about all of the fields for a specified project or all projects
	 * @param params an encapsulation of the user's credentials and a project id. If no project id is given, information about all of the fields in the database is returned.
	 * @return a list of information about each field
	 */
	public GetFields_Result GetFields(GetFields_Params params) {
		return null;
	}
	
	/**
	 * This method searches the indexed records for search terms
	 * @param params an encapsulation of the user's credentials, search fields, and search values
	 * @return a list of results matching the search criteria
	 */
	public Search_Result Search(Search_Params params) {
		return null;
	}
}