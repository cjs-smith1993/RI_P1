package shared.communication;

/**
 * This class is an encapsulation of the parameters of the GetFields command, which allows for easier XML serialization/deserialization.
 * @author Connor Smith
 *
 */
public class GetFields_Params {
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
	 * the unique id number of the project
	 */
	private int project_id;
	
//Constructors
	/**
	 * This constructor is an encapsulation of the GetFields parameters into a GetFields_Params object
	 * @param username the username of the user
	 * @param password the password of the user
	 * @param project_id the uniqeue id number of the project
	 */
	public GetFields_Params(String username, String password, int project_id) {
		super();
		this.username = username;
		this.password = password;
		this.project_id = project_id;
	}

//Getters
	/**
	 * This method returns the username of the user
	 * @return the username of the user
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
	 * This method returns the unique id number of the project
	 * @return the project_id
	 */
	public int getProject_id() {
		return project_id;
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
	 * This method sets the unique id number of the project
	 * @param project_id the project_id to set
	 */
	public void setProject_id(int project_id) {
		this.project_id = project_id;
	}

//Methods
	/**
	 * This method returns a String representation of the GetFields parameters
	 */
	@Override
	public String toString() {
		return this.username + "\n" + this.password + "\n" + this.project_id + "\n";
	}
}