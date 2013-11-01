package server.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import shared.communication.*;
import shared.model.*;

/**
 * This class is a representation of the users table in the database
 * @author Connor Smith
 *
 */
public class Users {
	
	/**
	 * This method returns a list of all users in the table
	 * @return a list of all users in the table
	 */
	public List<User> getUsers() {
		List<User> users = new ArrayList<User>();
		Connection connection = Database.getConnection();
		PreparedStatement prepstatement = null;
		ResultSet results = null;
		
		try {
			//Get the entire list of users in the database
			String getsql = "SELECT * FROM users";
			prepstatement = connection.prepareStatement(getsql);
			results = prepstatement.executeQuery();
			
			//Add each user to the list
			while (results.next()) {
				int id = results.getInt(1);
				String username = results.getString(2);
				String password = results.getString(3);
				String lastname = results.getString(4);
				String firstname = results.getString(5);
				String email = results.getString(6);
				int indexedrecords = results.getInt(7);
				int batch_id = results.getInt(8);
				
				users.add(new User(id, username, password, lastname, firstname, email, indexedrecords, batch_id));
			}

		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return users;
	}
	
	/**
	 * This method gets the user matching the user_id
	 * @param user_id the id of the user to get
	 * @return the matching user, or null if one doesn't exist
	 */
	public User getUser(int user_id) {
		User user = null;
		PreparedStatement prepstatement = null;
		ResultSet results = null;

		try {
			String getsql = "SELECT * FROM users WHERE id = ?";
			prepstatement = Database.getConnection().prepareStatement(getsql);
			prepstatement.setInt(1, user_id);
			results = prepstatement.executeQuery();
			//If there isn't a matching user, quit
			if (!results.isBeforeFirst())
				return null;

			String username = results.getString(2);
			String password = results.getString(3);
			String lastname = results.getString(4);
			String firstname = results.getString(5);
			String email = results.getString(6);
			int indexedrecords = results.getInt(7);
			int batch_id = results.getInt(8);
			user = new User(user_id, username, password, lastname, firstname, email, indexedrecords, batch_id);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}
	
	/**
	 * This method attempts to add a user
	 * @param user the user to be added
	 * @return the userid of the user that was inserted, or -1 if the insert failed
	 */
	public int add(User user) {		
		Connection connection = Database.getConnection();
		PreparedStatement prepstatement = null;
		Statement statement = null;
		ResultSet results = null;
		int user_id = -1;
		
		try {
			//Set up the SQL statement
			String addsql = "INSERT INTO users (username, password, lastname, firstname, email, indexedrecords, batch_id)"
							+ "VALUES (?,?,?,?,?,?,?)";
			
			//Fill unknowns with the user's information
			prepstatement = connection.prepareStatement(addsql);			
			prepstatement.setString(1, user.getUsername());
			prepstatement.setString(2, user.getPassword());
			prepstatement.setString(3, user.getLastname());
			prepstatement.setString(4, user.getFirstname());
			prepstatement.setString(5, user.getEmail());
			prepstatement.setInt(6, user.getIndexedRecords());
			prepstatement.setInt(7, user.getBatch_id());
						
			//If the user was added correctly
			if (prepstatement.executeUpdate() == 1) {
				//Set the user's id to the next id in the table
				statement = connection.createStatement();
				results = statement.executeQuery("SELECT last_insert_rowid()");
				results.next();
				user_id = results.getInt(1);
				user.setId(user_id);
			}
			else {
				System.out.println("ERROR: Insert failed.");
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return user_id;
	}
	
	/**
	 * This method updates the specified user
	 * @param user the user to be updated
	 * @return true if the operation is successful, otherwise false
	 */
	public boolean update(User user) {
		return false;
	}
	
	/**
	 * This method removes the specified user
	 * @param user the user to be removed
	 * @return true if the operation is successful, otherwise false
	 */
	public boolean remove(User user) {
		return false;
	}
	
	/**
	 * This method validates the user's credentials
	 * @param params an encapsulation of the user's credentials
	 * @return an encapsulation of the server's response
	 */
	public ValidateUser_Result ValidateUser(ValidateUser_Params params) {		
		//Loop through the list of users in the database
		List<User> users = getUsers();
		for (User user : users) {
			//If a match is found, return the user's information
			if (params.getUsername().equals(user.getUsername()) && params.getPassword().equals(user.getPassword())) {
				return new ValidateUser_Result(user.getFirstname(), user.getLastname(), user.getIndexedRecords(), "TRUE");
			}
		}
		//If no match is found, return false
		return new ValidateUser_Result("", "", 0, "FALSE");
	}
}