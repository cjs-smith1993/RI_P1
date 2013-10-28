package server.database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import shared.communication.*;
import shared.model.*;

/**
 * This class is a representation of the fields table in the database
 * @author Connor Smith
 *
 */
public class Fields {

	/**
	 * This method returns a list of all fields in the table
	 * @return a list of all fields in the table
	 */
	public List<Field> getFields() {
		List<Field> fields = new ArrayList<Field>();
		PreparedStatement prepstatement = null;
		ResultSet results = null;
		
		try {
			//Get the list of all fields in the database
			String getsql = "SELECT * FROM fields";
			prepstatement = Database.getConnection().prepareStatement(getsql);
			results = prepstatement.executeQuery();
			
			//Add each field to the list
			while (results.next()) {
				int id = results.getInt(1);
				String title = results.getString(2);
				int xcoord = results.getInt(3);
				int width = results.getInt(4);
				String helphtml = results.getString(5);
				String knowndata = results.getString(6);
				int field_num = results.getInt(7);
				int project_id = results.getInt(8);
				
				fields.add(new Field(id, title, xcoord, width, helphtml, knowndata, field_num, project_id));
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
		
		return fields;
	}
	
	/**
	 * This method attempts to add a field
	 * @param field the field to be added
	 * @return the id of the field added, or -1 if the insert failed
	 */
	public int add(Field field) {
		Connection connection = Database.getConnection();
		PreparedStatement prepstatement = null;
		Statement statement = null;
		ResultSet results = null;
		int fieldid = -1;
		
		try {
			//Set up the SQL statement
			String addsql = "INSERT INTO fields (title, xcoord, width, helphtml, knowndata, field_num, project_id)"
							+ "VALUES (?,?,?,?,?,?,?)";
			
			//Fill unknowns with the field's information
			prepstatement = connection.prepareStatement(addsql);			
			prepstatement.setString(1, field.getTitle());
			prepstatement.setInt(2, field.getXcoord());
			prepstatement.setInt(3, field.getWidth());
			prepstatement.setString(4, field.getHelphtml().getCanonicalPath());
			if (field.getKnowndata() != null)
				prepstatement.setString(5, field.getKnowndata().getCanonicalPath());
			else
				prepstatement.setString(5, null);
			prepstatement.setInt(6, field.getField_num());
			prepstatement.setInt(7, field.getProject_id());
						
			//If the field was added correctly
			if (prepstatement.executeUpdate() == 1) {
				//Set the field's id to the next id in the table
				statement = connection.createStatement();
				results = statement.executeQuery("SELECT last_insert_rowid()");
				results.next();
				fieldid = results.getInt(1);
				field.setId(fieldid);
			}
			else {
				System.out.println("ERROR: Insert failed.");
			}
		}
		catch (SQLException | IOException e) {
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
		return fieldid;
	}
	
	/**
	 * This method updates the specified field
	 * @param field the field to be updated
	 * @return true if the operation is successful, otherwise false
	 */
	public boolean update(Field field) {
		return false;
	}
	
	/**
	 * This method removes the specified field
	 * @param field the field to be removed
	 * @return true if the operation is successful, otherwise false
	 */
	public boolean remove(Field field) {
		return false;
	}
	
	/**
	 * This method returns information about all of the fields for a specified project or all projects
	 * @param params an encapsulation of the user's credentials and a project id. If no project id is given, information about all of the fields in the database is returned.
	 * @return a list of information about each field
	 */
	public GetFields_Result GetFields(GetFields_Params params) {
		return null;
	}
}
