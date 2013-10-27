package shared.model;

import org.w3c.dom.Element;

/**
 * This class serves as a model for a Project in the Projects table of the database
 * @author Connor Smith
 *
 */
public class Project {

//Fields
	/**
	 * the unique id number for this project (primary key)
	 */
	private int id;
	/**
	 * the title for this project (cannot be null, unique)
	 */
	private String title;
	/**
	 * the number of records that appear in each batch of this project (cannot be null)
	 */
	private int recordsperimage;
	/**
	 * the y-coordinate of the top of the first record on the images of this project (cannot be null, non-negative)
	 */
	private int firstycoord;
	/**
	 * the height of each record in the images of this project (cannot be null, positive, measured in pixels)
	 */
	private int recordheight;
	
//Constructors
	/**
	 * This constructor takes all required information and creates a new project for the database.
	 * @param id the unique id number for this project
	 * @param title the unique title for this project
	 * @param recordsperimage the number of records per image for this project
	 * @param firstycoord the y-coordinate of the top of the first record on the images of this project
	 * @param recordheight the height of each record in the images of this project
	 */
	public Project(int id, String title, int recordsperimage, int firstycoord,
			int recordheight) {
		this.id = id;
		this.title = title;
		this.recordsperimage = recordsperimage;
		this.firstycoord = firstycoord;
		this.recordheight = recordheight;
	}

	/**
	 * This constructor takes an Element as a parameter that contains all necessary information
	 * @param element The element that contains the project's information
	 */
	public Project(Element element) {
		this.id = -1;
		this.title = element.getElementsByTagName("title").item(0).getTextContent();
		this.recordsperimage = Integer.parseInt(element.getElementsByTagName("recordsperimage").item(0).getTextContent());
		this.firstycoord = Integer.parseInt(element.getElementsByTagName("firstycoord").item(0).getTextContent());
		this.recordheight = Integer.parseInt(element.getElementsByTagName("recordheight").item(0).getTextContent());
	}


	//Getters
	/**
	 * This method returns the unique id for this project.
	 * @return the unique id for this project
	 */
	public int getId() {
		return id;
	}

	/**
	 * This method returns the unique title for this project.
	 * @return the title for this project
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * This method returns the number of records that appear in each batch of this project
	 * @return the recordsperimage for this project
	 */
	public int getRecordsperimage() {
		return recordsperimage;
	}

	/**
	 * This method returns the y-coordinate of the top of the first record on the images of this project
	 * @return the firstycoord for this project
	 */
	public int getFirstycoord() {
		return firstycoord;
	}

	/**
	 * This method returns the height of each record in the images of this project
	 * @return the recordheight for this project
	 */
	public int getRecordheight() {
		return recordheight;
	}

	/**
	 * This method sets the unique id for this project
	 * @param id the id to set for this project
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * This method sets the unique title for this project
	 * @param title the title to set for this project
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * This method sets the number of records that appear in each batch of this project
	 * @param recordsperimage the recordsperimage to set for this project
	 */
	public void setRecordsperimage(int recordsperimage) {
		this.recordsperimage = recordsperimage;
	}

	/**
	 * This method sets the y-coordinate of the top of the first record on the images of this project
	 * @param firstycoord the firstycoord to set for this project
	 */
	public void setFirstycoord(int firstycoord) {
		this.firstycoord = firstycoord;
	}

	/**
	 * This method sets the height of each record in the images of this project
	 * @param recordheight the recordheight to set for this project
	 */
	public void setRecordheight(int recordheight) {
		this.recordheight = recordheight;
	}
	
//Methods
	/**
	 * This method returns a string representation of the project
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("id: " + id + "\n");
		sb.append("title: " + title + "\n");
		sb.append("recordsperimage: " + recordsperimage + "\n");
		sb.append("firstycoord: " + firstycoord + "\n");
		sb.append("recordheight: " + recordheight + "\n");
		
		return sb.toString();
	}
}