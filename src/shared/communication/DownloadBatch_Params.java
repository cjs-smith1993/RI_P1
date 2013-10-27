package shared.communication;

/**
 * This class is an encapsulation of the parameters of the DownloadBatch command, which allows for easier XML serialization/deserialization.
 * @author Connor Smith
 *
 */
public class DownloadBatch_Params {
//Fields
	/**
	 * the username for the user
	 */
	private String username;
	/**
	 * the password for the user
	 */
	private String password;
	/**
	 * the unique id number for the specified project
	 */
	private int project_id;

//Constructors
	/**
	 * This constructor is an encapsulation of the GetSampleImage parameters into a GetSampleImage_Params object
	 * @param username the username for the user
	 * @param password the password for the user
	 * @param project_id the unique id number for the specified project
	 */
	public DownloadBatch_Params(String username, String password,
			int project_id) {
		super();
		this.username = username;
		this.password = password;
		this.project_id = project_id;
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

	/**
	 * This method returns the unique id number of the specified project
	 * @return the specified project_id
	 */
	public int getProject_id() {
		return project_id;
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

	/**
	 * This method sets the unique id number of the specified project 
	 * @param project_id the specified project_id to set
	 */
	public void setProject_id(int project_id) {
		this.project_id = project_id;
	}
}