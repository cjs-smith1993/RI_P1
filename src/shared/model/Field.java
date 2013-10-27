package shared.model;

import java.io.File;
import java.io.IOException;
import org.w3c.dom.Element;

/**
 * This class serves as a model for a Field in the Fields table of the database
 * @author Connor Smith
 *
 */
public class Field {
	
//Fields
	/**
	 * the unique id number for this field (primary key)
	 */
	private int id;
	/**
	 * the title for this field (cannot be null)
	 */
	private String title;
	/**
	 * the x-coordinate of this field on the project's images (cannot be null)
	 */
	private int xcoord;
	/**
	 * the width of this field in the project's images (cannot be null)
	 */
	private int width;
	/**
	 * an HTML file that contains end-user help for this field (cannot be null)
	 */
	private File helphtml;
	/**
	 * a text file that contains known values for this field (a path, relative to the directory containing the XML file, to a file in the knowndata sub-directory
	 */
	private File knowndata;
	/**
	 * the field number for this field (cannot be null)
	 */
	private int field_num;
	/**
	 * the id of the project this field belongs to (cannot be null)
	 */
	private int project_id;
	
//Constructors
	/**
	 * This constructor takes all required information and creates a new field for the database.
	 * @param id the unique id for this field
	 * @param title the title for this field
	 * @param xcoord the x-coordinate of this field on the project's images
	 * @param width the width of this field in the project's images
	 * @param helphtml the location of an HTML file that contains end-user help for this field
	 * @param knowndata the location of a text file that contains known values for this field
	 * @param field_num the field number for this field
	 * @param project_id the id of the project this field corresponds to
	 */
	public Field(int id, String title, int xcoord, int width, String helphtml,
			String knowndata, int field_num, int project_id) {
		this.id = id;
		this.title = title;
		this.xcoord = xcoord;
		this.width = width;
		this.helphtml = new File(helphtml);
		this.knowndata = new File(knowndata);
		this.field_num = field_num;
		this.project_id = project_id;
	}
	
	/**
	 * This constructor takes an Element as a parameter that contains all necessary information
	 * @param element The element that contains the field's information
	 * @param field_num The field number for this field
	 * @param project_id The id of the project this field is associated with
	 */
	public Field(Element element, int field_num, int project_id) {
		this.id = -1;
		this.title = element.getElementsByTagName("title").item(0).getTextContent();
		this.xcoord = Integer.parseInt(element.getElementsByTagName("xcoord").item(0).getTextContent());
		this.width = Integer.parseInt(element.getElementsByTagName("width").item(0).getTextContent());
		String helphtml = element.getElementsByTagName("helphtml").item(0).getTextContent();
		this.helphtml = new File(helphtml);
		Element knowndata = (Element)element.getElementsByTagName("knowndata").item(0);
		if (knowndata != null)
			this.knowndata = new File(knowndata.getTextContent());
		else
			this.knowndata = null;
		this.field_num = field_num;
		this.project_id = project_id;
	}
	
//Getters
	/**
	 * This method returns the unique id number for this field
	 * @return the id for this field
	 */
	public int getId() {
		return id;
	}
	/**
	 * This method returns the title for this field
	 * @return the title for this field
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * This method returns the x-coordinate of this field on the project's images
	 * @return the xcoord for this field
	 */
	public int getXcoord() {
		return xcoord;
	}
	/**
	 * This method returns the width of this field in the project's images
	 * @return the width for this field
	 */
	public int getWidth() {
		return width;
	}
	/**
	 * This method returns the HTML file that contains end-user help for this field
	 * @return the helphtml for this field
	 */
	public File getHelphtml() {
		return helphtml;
	}
	/**
	 * This method returns the text file that contains known values for this field
	 * @return the knowndata for this field
	 */
	public File getKnowndata() {
		return knowndata;
	}
	/**
	 * This method returns the field number for this field
	 * @return the field_num for this field
	 */
	public int getField_num() {
		return field_num;
	}
	/**
	 * This method returns the id of the project this field corresponds to
	 * @return the project_id for this field
	 */
	public int getProject_id() {
		return project_id;
	}
	
//Setters
	/**
	 * This method sets the unique id number for this field
	 * @param id the id to set for this field
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * This method sets the title for this field
	 * @param title the title to set for this field
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * This method sets the x-coordinate of this field on the project's images
	 * @param xcoord the xcoord to set for this field
	 */
	public void setXcoord(int xcoord) {
		this.xcoord = xcoord;
	}
	/**
	 * This method sets the HTML file that contains end-user help for this field
	 * @param width the width to set for this field
	 */
	public void setWidth(int width) {
		this.width = width;
	}
	/**
	 * This method sets the 
	 * @param helphtml the location of the helphtml to set for this field
	 */
	public void setHelphtml(String helphtml) {
		this.helphtml = new File(helphtml);
	}
	/**
	 * This method sets the text file that contains known values for this field
	 * @param knowndata the location of the knowndata to set for this field
	 */
	public void setKnowndata(String knowndata) {
		this.knowndata = new File(knowndata);
	}
	/**
	 * This method sets the field number for this field
	 * @param field_num the field_num to set for this field
	 */
	public void setField_num(int field_num) {
		this.field_num = field_num;
	}
	/**
	 * This method sets the id of the project this field corresponds to
	 * @param project_id the project_id to set for this field
	 */
	public void setProject_id(int project_id) {
		this.project_id = project_id;
	}
	
//Methods
	/**
	 * This method returns a string representation of the field
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("id: " + id + "\n");
		sb.append("title: " + title + "\n");
		sb.append("xcoord: " + xcoord + "\n");
		sb.append("width: " + width + "\n");
		try {
			sb.append("helphtml: " + helphtml.getCanonicalPath() + "\n");
			if (knowndata != null)
				sb.append("knowndata: " + knowndata.getCanonicalPath() + "\n");
			else
				sb.append("knowndata: " + "none given\n");
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		sb.append("field_num: " + field_num + "\n");
		sb.append("project_id: " + project_id + "\n");
		
		return sb.toString();
	}
}