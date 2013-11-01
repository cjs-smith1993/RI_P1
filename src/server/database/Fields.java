package server.database;

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
	 * This method returns information about all of the fields for a specified project or all projects
	 * @param project_id the id of the project the requested fields belong to, or -1 if all fields are requested
	 * @return the list of matching fields
	 */
	public List<Field> getFields(int project_id) {
		List<Field> fields = null;
		
		PreparedStatement prepstatement = null;
		ResultSet results = null;
		
		String getsql;
		
		try {
			//Get the fields matching the project id. If none is specified, get all fields
			if (project_id == -1) {
				getsql = "SELECT * FROM fields";
				prepstatement = Database.getConnection().prepareStatement(getsql);
			}
			else {
				getsql = "SELECT * FROM fields WHERE project_id = ?";
				prepstatement = Database.getConnection().prepareStatement(getsql);
				prepstatement.setInt(1, project_id);
			}
			results = prepstatement.executeQuery();
			
			if (results.isBeforeFirst())
				fields = new ArrayList<Field>();
			
			//Add each field to the list
			while (results.next()) {
				int id = results.getInt(1);
				String title = results.getString(2);
				int xcoord = results.getInt(3);
				int width = results.getInt(4);
				String helphtml = results.getString(5);
				String knowndata = results.getString(6);
				int field_num = results.getInt(7);
				project_id = results.getInt(8);
				fields.add(new Field(id, title, xcoord, width, helphtml, knowndata, field_num, project_id));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return fields;
	}
	
	/**
	 * This method gets the field matching the field_id
	 * @param field_id the id of the field to get
	 * @return the matching field, or null if one doesn't exist
	 */
	public Field getField(int field_id) {
		Field field = null;
		PreparedStatement prepstatement = null;
		ResultSet results = null;

		try {
			//Get the field
			String getsql = "SELECT * FROM fields WHERE field_id = ?";
			prepstatement = Database.getConnection().prepareStatement(getsql);
			prepstatement.setInt(1, field_id);
			results = prepstatement.executeQuery();
			//If there isn't a matching field, quit
			if (!results.isBeforeFirst())
				return null;

			String title = results.getString(2);
			int xcoord = results.getInt(3);
			int width = results.getInt(4);
			String helphtml = results.getString(5);
			String knowndata = results.getString(6);
			int field_num = results.getInt(7);
			int project_id = results.getInt(8);
			field = new Field(field_id, title, xcoord, width, helphtml, knowndata, field_num, project_id);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return field;
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
		int field_id = -1;
		
		try {
			//Set up the SQL statement
			String addsql = "INSERT INTO fields (title, xcoord, width, helphtml, knowndata, field_num, project_id)"
							+ "VALUES (?,?,?,?,?,?,?)";
			
			//Fill unknowns with the field's information
			prepstatement = connection.prepareStatement(addsql);			
			prepstatement.setString(1, field.getTitle());
			prepstatement.setInt(2, field.getXcoord());
			prepstatement.setInt(3, field.getWidth());
			prepstatement.setString(4, field.getHelphtml());
			if (field.getKnowndata() != null)
				prepstatement.setString(5, field.getKnowndata());
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
				field_id = results.getInt(1);
				field.setId(field_id);
			}
			else {
				System.out.println("ERROR: Insert failed.");
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return field_id;
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
	 * This method returns an encapsulation of the list of fields for a specified project or all projects
	 * @param params an encapsulation of the user and desired project
	 * @return an encapsulation of the list of matching fields
	 */
	public GetFields_Result GetFields_Result(GetFields_Params params) {
		return new GetFields_Result(getFields(params.getProject_id()));
	}
}