package server.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import shared.model.*;

/**
 * This class is a representation of the values table in the database
 * @author Connor Smith
 *
 */
public class Values {
	
	/**
	 * This method returns a list of all values in the table
	 * @return a list of all records in the table
	 */
	public List<Value> getValues() {
		List<Value> values = new ArrayList<Value>();
		PreparedStatement prepstatement = null;
		ResultSet results = null;
		
		try {
			//Get the list of all values in the database
			String getsql = "SELECT * FROM record_values";
			prepstatement = Database.getConnection().prepareStatement(getsql);
			results = prepstatement.executeQuery();
			
			//Add each value to the list
			while (results.next()) {
				int id = results.getInt(1);
				String record_value = results.getString(2);
				int record_num = results.getInt(3);
				int field_num = results.getInt(4);
				int batch_id = results.getInt(5);
				
				values.add(new Value(id, record_value, record_num, field_num, batch_id));
			}

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
		
		return values;
	}
	
	/**
	 * This method attempts to add a value
	 * @param value the value to be added
	 * @return the id of the value added, or -1 if the insert failed
	 */
	public int add(Value value) {
		Connection connection = Database.getConnection();
		PreparedStatement prepstatement = null;
		Statement statement = null;
		ResultSet results = null;
		int valueid = -1;
		
		try {
			//Set up the SQL statement
			String addsql = "INSERT INTO record_values (record_value, record_num, field_num, batch_id)"
							+ "VALUES (?,?,?,?)";
			
			//Fill unknowns with the value's information
			prepstatement = connection.prepareStatement(addsql);			
			prepstatement.setString(1, value.getRecord_value());
			prepstatement.setInt(2, value.getRecord_num());
			prepstatement.setInt(3, value.getField_num());
			prepstatement.setInt(4, value.getBatch_id());
						
			//If the batch was added correctly
			if (prepstatement.executeUpdate() == 1) {
				//Set the batch's id to the next id in the table
				statement = connection.createStatement();
				results = statement.executeQuery("SELECT last_insert_rowid()");
				results.next();
				valueid = results.getInt(1);
				value.setId(valueid);
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
		return valueid;
	}
	
	/**
	 * This method updates the specified value
	 * @param value the value to be updated
	 * @return true if the operation is successful, otherwise false
	 */
	public boolean update(Value value) {
		return false;
	}
	
	/**
	 * This method removes the specified value
	 * @param value the value to be removed
	 * @return true if the operation is successful, otherwise false
	 */
	public boolean remove(Value value) {
		return false;
	}
}