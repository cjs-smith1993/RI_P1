package shared.communication;

import java.util.List;

/**
 * This class is an encapsulation of the parameters of the Search command, which allows for easier XML serialization/deserialization.
 * @author Connor Smith
 *
 */
public class Search_Params {
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
	 * the ids of the fields to search for
	 */
	private List<Integer> search_fields;
	/**
	 * the values to search for
	 */
	private List<String> search_values;
	
//Constructors
	/**
	 * This constructor is an encapsulation of the Search parameters into a Search_Params object
	 * @param username the username of the user
	 * @param password the password of the user
	 * @param search_fields the fields to search for
	 * @param search_values the values to search for
	 */
	public Search_Params(String username, String password,
			List<Integer> search_fields, List<String> search_values) {
		super();
		this.username = username;
		this.password = password;
		this.search_fields = search_fields;
		this.search_values = search_values;
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
	 * This method returns the fields to search for
	 * @return the search_fields
	 */
	public List<Integer> getSearch_fields() {
		return search_fields;
	}

	/**
	 * This method returns the values to search for
	 * @return the search_values
	 */
	public List<String> getSearch_values() {
		return search_values;
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
	 * This method sets the fields to search for
	 * @param search_fields the search_fields to set
	 */
	public void setSearch_fields(List<Integer> search_fields) {
		this.search_fields = search_fields;
	}

	/**
	 * This method sets the values to search for
	 * @param search_values the search_values to set
	 */
	public void setSearch_values(List<String> search_values) {
		this.search_values = search_values;
	}

//Methods
	/**
	 * This method returns a String representation of the Search parameters
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.username + "\n" + this.password + "\n");
		for (int i = 0; i < this.search_fields.size(); i++) {
			sb.append(this.search_fields.get(i));
			if (i < this.search_fields.size()-1)
				sb.append(",");
		}
		sb.append("\n");
		for (int i = 0; i < this.search_values.size(); i++) {
			sb.append(this.search_values.get(i));
			if (i < this.search_values.size()-1)
				sb.append(",");
		}
		sb.append("\n");
		return sb.toString();
	}
}