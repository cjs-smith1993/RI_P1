package shared.communication;

import java.io.IOException;
import java.util.List;
import shared.model.Field;

/**
 * This class is an encapsulation of the result of the DownloadBatch command, which allows for easier XML serialization/deserialization.
 * @author Connor Smith
 *
 */
public class DownloadBatch_Result {
//Fields
	/**
	 * the unique id number of the image
	 */
	private int batch_id;
	/**
	 * the unique id number of the project to which the batch belongs
	 */
	private int project_id;
	/**
	 * the url to the image
	 */
	private String image_url;
	/**
	 * the y-coordinate of the top of the first record in the image
	 */
	private int first_y_coord;
	/**
	 * the height of each record in the image
	 */
	private int record_height;
	/**
	 * the number of records in the image
	 */
	private int num_records;
	/**
	 * the number of fields in the image
	 */
	private int num_fields;
	/**
	 * the list of fields in the image
	 */
	private List<Field> fields;
	
//Constructors
	/**
	 * This constructor is an encapsulation of the DownloadBatch results into a DownloadBatch_Results object
	 * @param batch_id the unique id number of the image
	 * @param project_id the unique id number of the project to which the batch belongs
	 * @param image_url the url to the image
	 * @param first_y_coord the y-coordinate of the top of the first record in the image
	 * @param record_height the height of each recordi n the image
	 * @param num_records the number of records in the image
	 * @param num_fields the number of fields in the image
	 * @param fields the list of fields in the image
	 */
	public DownloadBatch_Result(int batch_id, int project_id, String image_url,
			int first_y_coord, int record_height, int num_records,
			int num_fields, List<Field> fields) {
		super();
		this.batch_id = batch_id;
		this.project_id = project_id;
		this.image_url = image_url;
		this.first_y_coord = first_y_coord;
		this.record_height = record_height;
		this.num_records = num_records;
		this.num_fields = num_fields;
		this.fields = fields;
	}

//Getters
	/**
	 * This method returns the unique id number of the image
	 * @return the batch_id
	 */
	public int getBatch_id() {
		return batch_id;
	}

	/**
	 * This method returns the unique id number of the project to which the batch belongs
	 * @return the project_id
	 */
	public int getProject_id() {
		return project_id;
	}

	/**
	 * This method returns the url to the image
	 * @return the image_url
	 */
	public String getImage_url() {
		return image_url;
	}

	/**
	 * This method returns the y-coordinate of the top of the first record in the image
	 * @return the first_y_coord
	 */
	public int getFirst_y_coord() {
		return first_y_coord;
	}

	/**
	 * This method returns the height of each record in the image
	 * @return the record_height
	 */
	public int getRecord_height() {
		return record_height;
	}

	/**
	 * This method returns the number of records in the image
	 * @return the num_records
	 */
	public int getNum_records() {
		return num_records;
	}

	/**
	 * This method returns the number of fields in the image
	 * @return the num_fields
	 */
	public int getNum_fields() {
		return num_fields;
	}

	/**
	 * This method returns the list of fields in the image
	 * @return the fields
	 */
	public List<Field> getFields() {
		return fields;
	}

//Setters
	/**
	 * This method sets the unique id number of the batch
	 * @param batch_id the batch_id to set
	 */
	public void setBatch_id(int batch_id) {
		this.batch_id = batch_id;
	}

	/**
	 * This method sets the unique id number of the project to which the batch belongs
	 * @param project_id the project_id to set
	 */
	public void setProject_id(int project_id) {
		this.project_id = project_id;
	}

	/**
	 * This method sets the url of the image
	 * @param image_url the image_url to set
	 */
	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}

	/**
	 * This method sets the y-coordninate of the top of the first record in the image
	 * @param first_y_coord the first_y_coord to set
	 */
	public void setFirst_y_coord(int first_y_coord) {
		this.first_y_coord = first_y_coord;
	}

	/**
	 * This method sets the height of each record in the image
	 * @param record_height the record_height to set
	 */
	public void setRecord_height(int record_height) {
		this.record_height = record_height;
	}

	/**
	 * This method sets the number of records in the batch
	 * @param num_records the num_records to set
	 */
	public void setNum_records(int num_records) {
		this.num_records = num_records;
	}

	/**
	 * This method sets the number of fields in the batch
	 * @param num_fields the num_fields to set
	 */
	public void setNum_fields(int num_fields) {
		this.num_fields = num_fields;
	}

	/**
	 * This method sets the list of fields in the batch
	 * @param fields the fields to set
	 */
	public void setFields(List<Field> fields) {
		this.fields = fields;
	}

//Methods
	/**
	 * This message returns all information of the result.
	 * If the operation fails, an appropriate message is printed
	 */
	@Override
	public String toString() {
		if (this.fields == null)
			return "FAILED\n";
		else {
			StringBuilder sb = new StringBuilder();
			sb.append(this.batch_id + "\n" + this.project_id + "\n" +
				this.image_url + "\n" + this.first_y_coord + "\n" +
				this.record_height + "\n" + this.num_records  +"\n" +
				this.num_fields + "\n");
			
			for (Field field : fields) {
				sb.append(field.getId() + "\n" + field.getField_num() + "\n" +
						field.getTitle() + "\n" + field.getHelphtml().toString() + "\n" +
						field.getXcoord() + "\n" + field.getWidth());
				if (field.getKnowndata() != null) {
					try {
						sb.append(field.getKnowndata().getCanonicalPath());
					}
					catch (IOException e) {e.printStackTrace();}
				}
			}
			return sb.toString();
		}
	}
}