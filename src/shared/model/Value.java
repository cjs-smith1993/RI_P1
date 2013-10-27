package shared.model;

import org.w3c.dom.Element;

/**
 * This class serves as a model for a Value in the Values table of the database
 * @author Connor Smith
 *
 */
public class Value {
//Fields
	/**
	 * the unique id number for this value (primary key)
	 */
	private int id;
	/**
	 * the value for this value
	 */
	private String value;
	/**
	 * the row this value in the batch it belongs to (cannot be null)
	 */
	private int record_num;
	/**
	 * the column of this value in the batch it belonds to
	 */
	private int field_num;
	/**
	 * the id number of the batch this value belongs to
	 */
	private int batch_id;

	
//Constructors
	public Value(int id, String value, int record_num, int batch_id,
			int field_num) {
		this.id = id;
		this.value = value;
		this.record_num = record_num;
		this.batch_id = batch_id;
		this.field_num = field_num;
	}

	/**
	 * This constructor takes an Element as a parameter that contains all necessary information
	 * @param element The element that contains the value's information
	 * @param record_num The index of this value in the batch it belongs to
	 * @param batch_id The id of the batch this value is associated with
	 */
	public Value(Element element, int record_num, int field_num, int batch_id) {
		this.id = -1;
		this.value = element.getTextContent();
		this.record_num = record_num;
		this.field_num = field_num;
		this.batch_id = batch_id;
	}
	
//Getters
	/**
	 * This method returns the unique id number for this value
	 * @return the id for this value
	 */
	public int getId() {
		return id;
	}

	/**
	 * This method returns the value for the value
	 * @return the value for this value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * This method returns the index of this value in the batch it belongs to
	 * @return the record_num for this value
	 */
	public int getRecord_num() {
		return record_num;
	}

	/**
	 * This method returns the id number of the batch this value belongs to
	 * @return the batch_id for this value
	 */
	public int getBatch_id() {
		return batch_id;
	}

	/**
	 * This method returns the number of the field this value corresponds to
	 * @return the field_num for this value
	 */
	public int getField_num() {
		return field_num;
	}

//Setters
	/**
	 * This method sets the unique id number for this value
	 * @param id the id to set for this value
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * This method sets the value for this value
	 * @param value the value to set for this value
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * This method sets the index of this value in the batch it belongs to
	 * @param record_num the record_num to set for this value
	 */
	public void setRecord_num(int record_num) {
		this.record_num = record_num;
	}

	/**
	 * This method sets the id number of the batch this value belongs to
	 * @param batch_id the batch_id to set for this value
	 */
	public void setBatch_id(int batch_id) {
		this.batch_id = batch_id;
	}

	/**
	 * This method sets the number of the field this value corresponds to
	 * @param field_num the field_num to set for this value
	 */
	public void setField_num(int field_num) {
		this.field_num = field_num;
	}
	
//Methods
	/**
	 * This method returns a string representation of the value
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("id: " + id + "\n");
		sb.append("value: " + value + "\n");
		sb.append("record_num: " + record_num + "\n");
		sb.append("field_num: " + field_num + "\n");
		sb.append("batch_id: " + batch_id + "\n");

		return sb.toString();
	}
}