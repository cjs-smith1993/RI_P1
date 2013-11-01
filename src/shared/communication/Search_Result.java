package shared.communication;

import java.util.List;

/**
 * This class is an encapsulation of the result of the Search command, which allows for easier XML serialization/deserialization.
 * @author Connor Smith
 *
 */
public class Search_Result {
	
	/**
	 * the list of results matching the search
	 */
	private List<Result> results1;
	
	//Constructors
	/**
	 * This constructor is an encapsulation of the Search results into a DownloadBatch_Results object
	 * @param results The list of results
	 */
	public Search_Result(List<Result> results) {
		super();
		this.results1 = results;
	}
	
	//Getters
	/**
	 * This method returns the list of results matching the search
	 * @return the search_results
	 */
	public List<Result> getResults() {
		return results1;
	}
	
	//Setters
	/**
	 * This method sets the list of results matching the search
	 * @param results the search_results to set
	 */
	public void setSearch_results(List<Result> results) {
		this.results1 = results;
	}
	
	//Methods
	/**
	 * This method prints the list of results matching the search
	 */
	@Override
	public String toString() {
		if (this.results1 == null)
			return "FAILED\n";
		else {
			StringBuilder sb = new StringBuilder();
			for (Result result : results1) {
				sb.append(result.toString());
			}
			return sb.toString();
		}
	}
}