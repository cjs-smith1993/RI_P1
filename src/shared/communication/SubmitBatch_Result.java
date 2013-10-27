package shared.communication;

/**
 * This class is an encapsulation of the result of the SubmitBatch command, which allows for easier XML serialization/deserialization.
 * @author Connor Smith
 *
 */
public class SubmitBatch_Result {
//Fields
	/**
	 * the success of the batch submission
	 */
	private boolean success;

//Constructors
	/**
	 * This constructor is an encapsulation of the SubmitBatch results into a DownloadBatch_Results object
	 * @param success
	 */
	public SubmitBatch_Result(boolean success) {
		super();
		this.success = success;
	}

//Getters
	/**
	 * This method returns whether the batch submission was successful
	 * @return the success of the batch submission
	 */
	public boolean isSuccess() {
		return success;
	}

//Setters
	/**
	 * This method sets whether the batch submission was successful
	 * @param success the success to set
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
//Methods
	/**
	 * This method prints out whether the batch submission was successful
	 */
	@Override
	public String toString() {
		if (success)
			return "TRUE\n";
		else
			return "FAILED\n";
	}
}