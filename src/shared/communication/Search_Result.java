package shared.communication;

import java.util.List;

/**
 * This class is an encapsulation of the result of the Search command, which allows for easier XML serialization/deserialization.
 * @author Connor Smith
 *
 */
public class Search_Result {
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
	/**
	 * the list of results matching the search
	 */
	private List<Search_Result> search_results;

//Constructors
	/**
	 * This constructor is an encapsulation of the Search results into a DownloadBatch_Results object
	 * @param search_results The list of results
	 */
	public Search_Result(List<Search_Result> search_results) {
		super();
		this.search_results = search_results;
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
	
	/**
	 * This method returns the list of results matching the search
	 * @return the search_results
	 */
	public List<Search_Result> getSearch_results() {
		return search_results;
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
	
	/**
	 * This method sets the list of results matching the search
	 * @param search_results the search_results to set
	 */
	public void setSearch_results(List<Search_Result> search_results) {
		this.search_results = search_results;
	}
	
//Methods

	/**
	 * This method prints the list of results matching the search
	 */
	@Override
	public String toString() {
		if (this.search_results == null)
			return "FAILED\n";
		else {
			StringBuilder sb = new StringBuilder();
			for (Search_Result result : search_results) {
				sb.append(result.getBatch_id() + "\n" + result.getImage_url() + "\n" +
						result.getRecord_num() + "\n" + result.getField_id() + "\n");
			}
			return sb.toString();
		}
	}
}