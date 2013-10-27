package shared.model;

import org.w3c.dom.Element;

/**
 * This class serves as a model for a User in the Users table of the database
 * @author Connor Smith
 *
 */
public class User {

//Fields
	/**
	 * the unique id number for this user (primary key)
	 */
	private int id;
	/**
	 * the username for this user (cannot be null, must be unique)
	 */
	private String username;
	/**
	 * the password for this user (cannot be null)
	 */
	private String password;
	/**
	 * the last name for this user (cannot be null)
	 */
	private String lastname;
	/**
	 * the first name for this user (cannot be null)
	 */
	private String firstname;
	/**
	 * the email address for this user (cannot be null)
	 */
	private String email;
	/**
	 * the number of records this user has indexed (cannot be null, non-negative)
	 */
	private int indexedrecords;
	/**
	 * the id number of the batch this user is currently working on
	 */
	private int batch_id;
	
//Constructors
	/**
	 * This constructor takes all required information and creates a new user for the database.
	 * @param id the unique id number for this user
	 * @param username the username for this user
	 * @param password the password for this user
	 * @param lastname the last name for this user
	 * @param firstname the first name for this user
	 * @param email the email address for this user
	 * @param batch_id the id of the batch this user is currently working on
	 */
	public User (int id, String username, String password, String lastname,
			String firstname, String email, int indexedrecords, int batch_id){
		this.id = id;
		this.username = username;
		this.password = password;
		this.lastname = lastname;
		this.firstname = firstname;
		this.email = email;
		this.indexedrecords = indexedrecords;
		this.batch_id = batch_id;		
	}
	
	/**
	 * This constructor takes an Element as a parameter that contains all necessary information
	 * @param element The element that contains the user's information
	 */
	public User(Element element) {
		this.id = -1;
		this.username = element.getElementsByTagName("username").item(0).getTextContent();
		this.password = element.getElementsByTagName("password").item(0).getTextContent();
		this.firstname = element.getElementsByTagName("firstname").item(0).getTextContent();
		this.lastname = element.getElementsByTagName("lastname").item(0).getTextContent();
		this.email = element.getElementsByTagName("email").item(0).getTextContent();
		this.indexedrecords = Integer.parseInt(element.getElementsByTagName("indexedrecords").item(0).getTextContent());
		this.batch_id = -1;
	}
	
//Getters
	/**
	 * This method returns the unique id number for this user.
	 * @return the unique id number for this user
	 */
	public int getId() {
		return id;
	}

	/**
	 * This method returns the username for this user.
	 * @return the username for this user
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * This method returns the password for this user.
	 * @return the password for this user
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * This method returns the last name for this user.
	 * @return the lastname for this user
	 */
	public String getLastname() {
		return lastname;
	}

	/**
	 * This method returns the first name for this user.
	 * @return the firstname for this user
	 */
	public String getFirstname() {
		return firstname;
	}

	/**
	 * This method returns the email for this user.
	 * @return the email for this user
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * This method returns the number of records this user has indexed.
	 * @return the number of records this user has indexed
	 */
	public int getIndexedRecords() {
		return indexedrecords;
	}
	
	/**
	 * This method returns the id of the batch that this user is working on.
	 * @return the batch_id for this user
	 */
	public int getBatch_id() {
		return batch_id;
	}

//Setters
	/**
	 * This method sets the unique id number for this user.
	 * @param id the unique id number to set for this user
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * This method sets the username for this user.
	 * @param username the username to set for this user
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * This method sets the password for this user.
	 * @param password the password to set for this user
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * This method sets the last name for this user.
	 * @param lastname the lastname to set for this user
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	/**
	 * This method sets the first name for this user.
	 * @param firstname the firstname to set for this user
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	/**
	 * This method sets the email for this user.
	 * @param email the email to set for this user
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * This method adds one to the number of records this user has indexed
	 */
	public void addRecord() {
		this.indexedrecords++;
	}
	
	/**
	 * This method sets the id of the batch this user is working on.
	 * @param batch_id the batch_id to set for this user
	 */
	public void setBatch_id(int batch_id) {
		this.batch_id = batch_id;
	}
	
//Methods
	/**
	 * This method returns a string representation of the user
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("id: " + id + "\n");
		sb.append("username: " + username + "\n");
		sb.append("password: " + password + "\n");
		sb.append("lastname: " + lastname + "\n");
		sb.append("firstname: " + firstname + "\n");
		sb.append("email: " + email + "\n");
		sb.append("indexedrecords: " + indexedrecords + "\n");
		sb.append("batch_id: " + batch_id + "\n");
		
		return sb.toString();
	}
}