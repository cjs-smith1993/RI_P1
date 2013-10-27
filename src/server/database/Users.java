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
//Fields
	
//Constructors
	
//Getters
	
//Setters

//Methods
	/**
	 * This method returns a list of all users in the table
	 * @return a list of all users in the table
	 */
	public List<User> getUsers() {
		List<User> users = new ArrayList<User>();
		PreparedStatement prepstatement = null;
		ResultSet results = null;
		
		try {
			String getsql = "SELECT * FROM users";
			prepstatement = Database.getConnection().prepareStatement(getsql);
			
			results = prepstatement.executeQuery();
			int id = results.getInt(1);
			String username = results.getString(2);
			String password = results.getString(3);
			String lastname = results.getString(4);
			String firstname = results.getString(5);
			String email = results.getString(6);
			int indexedrecords = results.getInt(7);
			int batch_id = results.getInt(8);
			User user = new User(id, username, password, lastname, firstname, email, indexedrecords, batch_id);
			users.add(user);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				if (prepstatement != null)
					prepstatement.close();
				if (results != null)
					results.close();
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return users;
	}
	
	/**
	 * This method attempts to add a user
	 * @param user the user to be added
	 * @return the userid of the user that was inserted, or -1 if the insert failed
	 */
	public int add(User user) {		
		Connection connection = Database.getConnection(); //Get a connection to the SQL database
		PreparedStatement prepstatement = null;
		Statement statement = null;
		ResultSet results = null;
		int userid = -1;
		
		try {
			//Set up the SQL statement
			String addsql = "INSERT INTO users (username, password, lastname, firstname, email, indexedrecords, batch_id)"
							+ "VALUES (?,?,?,?,?,?,?)";
			
			prepstatement = connection.prepareStatement(addsql);			
			prepstatement.setString(1, user.getUsername());
			prepstatement.setString(2, user.getPassword());
			prepstatement.setString(3, user.getLastname());
			prepstatement.setString(4, user.getFirstname());
			prepstatement.setString(5, user.getEmail());
			prepstatement.setInt(6, user.getIndexedRecords());
			prepstatement.setInt(7, user.getBatch_id());
						
			if (prepstatement.executeUpdate() == 1) { //If a user was added correctly
				statement = connection.createStatement();
				results = statement.executeQuery("SELECT last_insert_rowid()");
				results.next();
				userid = results.getInt(1);
				user.setId(userid);
			}
			else {
				System.out.println("ERROR: Insert failed.");
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				if (prepstatement != null)
					prepstatement.close();
				if (statement != null)
					statement.close();
				if (results != null)
					results.close();
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return userid;
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
		User u = new User(0, "sheila", "parker", "Sheila", "Parker", "sheila.parker@gmail.com", 0, -1);
		add(u);
		
		//Loop through the list of users in the database
		List<User> users = getUsers();
		for (User user : users) {
			System.out.println(user.getUsername());
			//If a match is found, return the user's information
			if (params.getUsername().equals(user.getUsername()) && params.getPassword().equals(user.getPassword())) {
				return new ValidateUser_Result(user.getFirstname(), user.getLastname(), user.getIndexedRecords(), "TRUE");
			}
		}
		return null;
	}
}
