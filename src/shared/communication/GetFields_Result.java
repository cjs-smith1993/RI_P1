package shared.communication;

import java.util.List;
import shared.model.Field;

/**
 * This class is an encapsulation of the result of the GetFields command, which allows for easier XML serialization/deserialization.
 * @author Connor Smith
 *
 */
public class GetFields_Result {
//Fields
	/**
	 * The list of fields for the project
	 */
	private List<Field> fields;

//Constructors
	/**
	 * This constructor is an encapsulation of the GetFields results into a DownloadBatch_Results object
	 * @param fields
	 */
	public GetFields_Result(List<Field> fields) {
		super();
		this.fields = fields;
	}

//Getters
	/**
	 * This method returns the list of fields for the project
	 * @return the fields
	 */
	public List<Field> getFields() {
		return fields;
	}
	
//Setters
	/**
	 * This method sets the list of fields for the project
	 * @param fields the fields to set
	 */
	public void setFields(List<Field> fields) {
		this.fields = fields;
	}
	
//Methods
	/**
	 * This method prints information about all of the fields for the project.
	 * If the operation fails, an appropriate message is printed.
	 */
	@Override
	public String toString() {
		if (this.fields == null)
			return "FAILED\n";
		else {
			String result = "";
			for (Field field : fields) {
				result += field.getProject_id() + "\n" + field.getId() + "\n" + field.getTitle() + "\n";
			}
			return result;
		}
	}
}