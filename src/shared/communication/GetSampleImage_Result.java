package shared.communication;

/**
 * This class is an encapsulation of the result of the GetSampleImage_Result command, which allows for easier XML serialization/deserialization.
 * @author Connor Smith
 *
 */
public class GetSampleImage_Result {
//Fields
	/**
	 * the url to a sample image in the project
	 */
	private String image_url;

//Constructors
	/**
	 * This constructor is an encapsulation of the GetSampleImage results into a GetSampleImage_Results object
	 * @param image_url the url to a sample image in the project
	 */
	public GetSampleImage_Result(String image_url) {
		super();
		this.image_url = image_url;
	}

//Getters
	/**
	 * This method returns the url to a sample image in the project
	 * @return the image_url to a sample image in the project
	 */
	public String getImage_url() {
		return image_url;
	}

//Setters
	/**
	 * This method sets the url to a sample image in the project
	 * @param image_url the image_url to set
	 */
	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}
	
//Methods
	/**
	 * This method returns the url to a sample image in the project.
	 * If the operation fails, an appropriate message is printed.
	 */
	@Override
	public String toString() {
		if (this.image_url == null)
			return "FAILED\n";
		else
			return this.image_url + "\n";
	}
}