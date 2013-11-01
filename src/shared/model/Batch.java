package shared.model;

import org.w3c.dom.Element;

/**
 * This class serves as a model for a Batch in the Batches table of the database
 * @author Connor Smith
 *
 */
public class Batch {
	
//Fields
	/**
	 * the unique id number for this batch (primary key)
	 */
	private int id;
	/**
	 * the path to the PNG file that contains the image for this batch (cannot be null, unique)
	 */
	private String file_name;
	/**
	 * the id number of the project this batch belongs to (cannot be null)
	 */
	private int project_id;
	/**
	 * the id number of the user currently working on this batch (cannot be null)
	 */
	private int user_id;
	/**
	 * the current state of this batch (-1 for unopened, 0 for opened, 1 for completed)
	 */
	private int state;
	
//Constructors
	/**
	 * This constructor takes all required information and creates a new field for the database.
	 * @param id the unique id number for this batch
	 * @param file the location of a PNG file that contains the image for this batch
	 * @param project_id the id number of the project this batch corresponds to
	 * @param user_id the id number of the user currently working on this batch
	 * @param state the current state of this batch
	 */
	public Batch(int id, String file_name, int project_id, int user_id, int state) {
		this.id = id;
		this.file_name = file_name;
		this.project_id = project_id;
		this.user_id = user_id;
		this.state = state;
	}

	/**
	 * This constructor takes an Element as a parameter that contains all necessary information
	 * @param element The element that contains the batch's information
	 * @param project_id The id of the project this field is associated with
	 */
	public Batch(Element element, int project_id) {
		String file_name = element.getElementsByTagName("file").item(0).getTextContent();
		this.id = -1;
		this.file_name = file_name;
		this.project_id = project_id;
		this.user_id = -1;
		this.state = -1;
	}
	
//Getters
	/**
	 * This method returns the unique id number for this batch
	 * @return the unique id number for this batch
	 */
	public int getId() {
		return id;
	}

	/**
	 * This method returns the path to the PNG file that contains the image for this batch
	 * @return the file_name for this batch
	 */
	public String getFile() {
		return file_name;
	}

	/**
	 * This method returns the id number of the project this batch corresponds to
	 * @return the project_id for this batch
	 */
	public int getProject_id() {
		return project_id;
	}

	/**
	 * This method returns the id number of the user currently working on this batch
	 * @return the user_id for this batch
	 */
	public int getUser_id() {
		return user_id;
	}

	/**
	 * This method returns the current state of this batch
	 * @return the state for this batch
	 */
	public int getState() {
		return state;
	}
	
//Setters
	/**
	 * This method sets the unique id number for this batch
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * This method sets the path to the PNG file that contains the image for this batch
	 * @param file_name the location of the file to set for this batch
	 */
	public void setFile(String file_name) {
		this.file_name = file_name;
	}

	/**
	 * This method sets the unique id number for this batch 
	 * @param project_id the project_id to set for this batch
	 */
	public void setProject_id(int project_id) {
		this.project_id = project_id;
	}

	/**
	 * This method sets the id number of the user currently working on this batch
	 * @param user_id the user_id to set for this batch
	 */
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	/**
	 * This method sets the current state of this batch
	 * @param state the state to set for this batch
	 */
	public void setState(int state) {
		this.state = state;
	}
	
//Methods
	/**
	 * This method returns a string representation of the batch
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("id: " + id + "\n");
		sb.append("file: " + file_name + "\n");
		sb.append("project_id: " + project_id + "\n");
		sb.append("user_id: " + user_id + "\n");
		sb.append("state: " + state + "\n");

		return sb.toString();
	}
}