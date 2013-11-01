package shared.communication;

/**
 * This class is an encapsulation of the parameters of the ValidateUser command, which allows for easier XML serialization/deserialization.
 * @author Connor Smith
 *
 */
public class ValidateUser_Params {
//Fields
	/**
	 * the username for the user
	 */
	private String username;
	/**
	 * the password for the user
	 */
	private String password;
	
//Constructors
	/**
	 * This constructor is an encapsulation of the ValidateUser parameters into a ValidateUser_Params object
	 * @param username the username for the user
	 * @param password the password for the user
	 */
	public ValidateUser_Params(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

//Getters
	/**
	 * This method returns the username for the user
	 * @return the username for the user
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * This method returns the password for the user
	 * @return the password for the user
	 */
	public String getPassword() {
		return password;
	}

//Setters
	/**
	 * This method sets the username for the user
	 * @param username the username to set for the user
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * This method sets the password for the user
	 * @param password the password to set for the user
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
//Methods
	/**
	 * This method returns a String representation of the ValidateUser parameters
	 */
	@Override
	public String toString() {
		return this.username + "\n" + this.password + "\n";
	}
}