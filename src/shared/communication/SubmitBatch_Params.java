package shared.communication;

/**
 * This class is an encapsulation of the parameters of the SubmitBatch command, which allows for easier XML serialization/deserialization.
 * @author Connor Smith
 *
 */
public class SubmitBatch_Params {
//Fields
	/**
	 * the username of the user
	 */
	private String username;
	/**
	 * the password of the user
	 */
	private String password;
	/**
	 * the unique id number of the batch
	 */
	private int batch_id;
	/**
	 * the field values of the batch
	 */
	private String field_values;
	
//Constructors
	/**
	 * This constructor is an encapsulation of the SubmitBatch parameters into a SubmitBatch_Params object
	 * @param username
	 * @param password
	 * @param batch_id
	 * @param field_values
	 */
	public SubmitBatch_Params(String username, String password, int batch_id,
			String field_values) {
		super();
		this.username = username;
		this.password = password;
		this.batch_id = batch_id;
		this.field_values = field_values;
	}

//Getters
	/**
	 * This method returns the username of the user
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * This method returns the password of the user
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * This method returns the unique id number of the batch
	 * @return the batch_id
	 */
	public int getBatch_id() {
		return batch_id;
	}

	/**
	 * This method returns the field values of the batch
	 * @return the field_values
	 */
	public String getField_values() {
		return field_values;
	}

//Setters
	/**
	 * This method sets the username of the user
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * This method sets the password of the user
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * This method sets the unique id number of the batch
	 * @param batch_id the batch_id to set
	 */
	public void setBatch_id(int batch_id) {
		this.batch_id = batch_id;
	}

	/**
	 * This method sets the field values of the batch
	 * @param field_values the field_values to set
	 */
	public void setField_values(String field_values) {
		this.field_values = field_values;
	}
}