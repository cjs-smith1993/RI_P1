package server.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;
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
	private static String RESET_FILE = "database" + File.separator + "indexer_server.sql";
	
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
	public Users users() {
		return users;
	}

	/**
	 * This methods returns the projects table
	 * @return the projects table
	 */
	public Projects projects() {
		return projects;
	}

	/**
	 * This methods returns the fields table
	 * @return the fields table
	 */
	public Fields fields() {
		return fields;
	}

	/**
	 * This methods returns the batches table
	 * @return the batches table
	 */
	public Batches batches() {
		return batches;
	}

	/**
	 * This methods returns the records table
	 * @return the records table
	 */
	public Values values() {
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
	 * This method removes all data from the database and recreates the tables
	 */
	public void reset() {
		Connection connection = Database.getConnection(); //Get a connection to the SQL database
		String[] statements = null;
		Statement statement = null;

		//Get all statements from the reset file
		try {
			StringBuilder sb = new StringBuilder();
			Scanner s = new Scanner(new File(RESET_FILE));
			while (s.hasNextLine()) {
				sb.append(s.nextLine() + "\n");
			}
			s.close();
			//Send each statement to an individual string (remember the semicolon is now gone)
			statements = sb.toString().split(";");
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		//Execute each statement
		for (int i = 0; i < statements.length-1; i++) {
			try {
				statement = connection.createStatement();
				//Remember to add the semicolon back to each statement
				statement.executeUpdate(statements[i] + ";");
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
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
		return projects.GetProjects_Result(params);
	}
	
	/**
	 * This method gets a sample image from a specified project
	 * @param params an encapsulation of the user's credentials and a project id
	 * @return a url to a sample image
	 */
	public GetSampleImage_Result GetSampleImage(GetSampleImage_Params params) {
		return batches.GetSampleImage(params);
	}
	
	/**
	 * This method downloads a batch for the user
	 * @param params an encapsulation of the user's credentials and a project id
	 * @return a complete batch from the specified project
	 */
	public DownloadBatch_Result DownloadBatch(DownloadBatch_Params params) {
		return batches.DownloadBatch(params);
	}
	
	/**
	 * This method submits an indexed batch to the server
	 * @param params an encapsulation of the user's credentials, a batch id, and a list of field values
	 * @return success or failure based on the server's response
	 */
	public SubmitBatch_Result SubmitBatch(SubmitBatch_Params params) {
		return batches.SubmitBatch(params);
	}
	
	/**
	 * This method returns information about all of the fields for a specified project or all projects
	 * @param params an encapsulation of the user's credentials and a project id. If no project id is given, information about all of the fields in the database is returned.
	 * @return a list of information about each field
	 */
	public GetFields_Result GetFields(GetFields_Params params) {
		return fields.GetFields_Result(params);
	}
	
	/**
	 * This method searches the indexed records for search terms
	 * @param params an encapsulation of the user's credentials, search fields, and search values
	 * @return a list of results matching the search criteria
	 */
	public Search_Result Search(Search_Params params) {
		Search_Result result = new Search_Result(null);
		ArrayList<Integer> search_fields = (ArrayList<Integer>)params.getSearch_fields();
		ArrayList<String> search_values = (ArrayList<String>)params.getSearch_values();
		
		ArrayList<Result> search_results = new ArrayList<Result>();
		
		Connection connection = Database.getConnection();
		PreparedStatement prepstatement = null;
		ResultSet results = null;
		
		try {
			//Try every combination of field id and search value
			for (Integer field_id : search_fields) {
				for (String value : search_values) {
					//Get all record values, record number, field id, batch it, and image URL from the database
					String sql = "SELECT record_value, record_num, fields.id AS field_id, batch_id, file_name "
							+ "FROM record_values, batches, fields "
							+ "WHERE record_values.batch_id = batches.id AND record_values.field_num = fields.field_num "
							+ "AND batches.project_id = fields.project_id ";
					//Now select out only matching records
					sql += "AND record_value = ? AND field_id = ?";
					prepstatement = connection.prepareStatement(sql);
					prepstatement.setString(1, value.toUpperCase());
					prepstatement.setInt(2, field_id);
					results = prepstatement.executeQuery();
					
					//If there isn't a match, move on. Otherwise, get the match
					if (!results.isBeforeFirst())
						continue;
					while (results.next()) {
						int batch_id = results.getInt(4);
						String image_url = results.getString(5);
						int record_num = results.getInt(2);
						search_results.add(new Result(batch_id, image_url, record_num, field_id));
					}
				}
			}
			if (search_results.size() == 0)
				return result;
			result.setSearch_results(search_results);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
}