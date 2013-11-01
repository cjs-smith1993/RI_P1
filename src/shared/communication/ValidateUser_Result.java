package shared.communication;

/**
 * This class is an encapsulation of the result of the ValidateUser command, which allows for easier XML serialization/deserialization.
 * @author Connor Smith
 *
 */
public class ValidateUser_Result {
//Fields
	/**
	 * the first name for the user
	 */
	private String firstname;
	/**
	 * the last name for the user
	 */
	private String lastname;
	/**
	 * the number of records the user has indexed
	 */
	private int indexedrecords;
	/**
	 * the validity of the user (TRUE, FALSE, or FAILED)
	 */
	private String validation;
	
//Constructors
	/**
	 * This constructor is an encapsulation of the ValidateUser results into a ValidateUser_Result object
	 * @param firstname the first name for the user
	 * @param lastname the last name for the user
	 * @param indexedrecords the number of records the user has indexed
	 */
	public ValidateUser_Result(String firstname, String lastname,
			int indexedrecords, String valid) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.indexedrecords = indexedrecords;
		this.validation = valid;
	}
	
//Getters
	/**
	 * This method returns the first name for the user
	 * @return the firstname for the user
	 */
	public String getFirstname() {
		return firstname;
	}
	
	/**
	 * This method returns the last name for the user
	 * @return the lastname for the user
	 */
	public String getLastname() {
		return lastname;
	}
	
	/**
	 * This method returns the number of records the user has indexed
	 * @return the indexedrecords for the user
	 */
	public int getIndexedrecords() {
		return indexedrecords;
	}
	
	/**
	 * This method returns the validity of the user
	 * @return the validation of the user
	 */
	public String getValidity() {
		return validation;
	}
	
	/**
	 * This method returns whether or not the user is valid
	 * @return true if the user is valid, otherwise false
	 */
	public boolean isValid() {
		return validation.equals("TRUE");
	}
	
//Setters
	/**
	 * This method sets the first name for the user
	 * @param firstname the firstname to set for the user
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	/**
	 * This method sets the last name for the user
	 * @param lastname the lastname to set for the user
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	/**
	 * This method sets the number of records the user has indexed
	 * @param indexedrecords the indexedrecords to set for the user
	 */
	public void setIndexedrecords(int indexedrecords) {
		this.indexedrecords = indexedrecords;
	}
	
	/**
	 * This method sets the validity of the user
	 * @param valid the validity of the user
	 */
	public void setValidation(String valid) {
		this.validation = valid;
	}
	
//Methods
	/**
	 * This method will print out a visual representation of the user.
	 * If the user's credentials are invalid or the operation fails, a corresponding message will be printed.
	 */
	@Override
	public String toString() {
		if (this.validation.equals("TRUE"))
			return this.validation + "\n" + this.firstname + "\n" 
				+ this.lastname + "\n" + this.indexedrecords + "\n";
		else
			return this.validation + "\n";
	}
}