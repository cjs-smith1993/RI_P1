package server.database;

import java.util.List;
import shared.communication.*;
import shared.model.*;

/**
 * This class is a representation of the fields table in the database
 * @author Connor Smith
 *
 */
public class Fields {
//Fields

//Constructors

//Getters

//Setters

//Methods
	
	/**
	 * This method returns a list of all fields in the table
	 * @return a list of all fields in the table
	 */
	public List<Field> getFields() {
		return null;
	}
	
	/**
	 * This method attempts to add a field
	 * @param field the field to be added
	 * @return true if the operation is successful, otherwise false
	 */
	public boolean add(Field field) {
		return false;
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
