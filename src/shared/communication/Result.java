package shared.communication;

public class Result {
//Fields
	/**
	 * The id of the batch containing this result
	 */
	private int batch_id;
	/**
	 * The URL of the image file
	 */
	private String image_url;
	/**
	 * The index of the record in the batch that contains this result
	 */
	private int record_num;
	/**
	 * The id of the field in the batch that contains this result
	 */
	private int field_id;
	
//Constructors
	/**
	 * This constructor is an encapsulation of the Search results into a Result object
	 * @param batch_id the id of the batch that contains the matching result
	 * @param image_url the URL to the image that contains the result
	 * @param record_num the row in the batch for this result
	 * @param field_id the id this result corresponds to
	 */
	public Result(int batch_id, String image_url, int record_num, int field_id) {
		this.batch_id = batch_id;
		this.image_url = image_url;
		this.record_num = record_num;
		this.field_id = field_id;
	}
	
//Getters
	/**
	 * @return the batch_id
	 */
	public int getBatch_id() {
		return batch_id;
	}

	/**
	 * @return the image_url
	 */
	public String getImage_url() {
		return image_url;
	}

	/**
	 * @return the record_num
	 */
	public int getRecord_num() {
		return record_num;
	}

	/**
	 * @return the field_id
	 */
	public int getField_id() {
		return field_id;
	}

//Setters
	/**
	 * @param batch_id the batch_id to set
	 */
	public void setBatch_id(int batch_id) {
		this.batch_id = batch_id;
	}

	/**
	 * @param image_url the image_url to set
	 */
	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}

	/**
	 * @param record_num the record_num to set
	 */
	public void setRecord_num(int record_num) {
		this.record_num = record_num;
	}

	/**
	 * @param field_id the field_id to set
	 */
	public void setField_id(int field_id) {
		this.field_id = field_id;
	}
	
//Methods
	/**
	 * This method prints the list of results matching the search
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getBatch_id() + "\n" + getImage_url() + "\n" +
					getRecord_num() + "\n" + getField_id() + "\n");
		return sb.toString();
	}
}