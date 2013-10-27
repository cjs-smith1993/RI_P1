package server.database;

import java.util.List;
import shared.communication.*;
import shared.model.*;

/**
 * This class is a representation of the batches table in the database
 * @author Connor Smith
 *
 */
public class Batches {
	//Fields
	
	//Constructors
		
	//Getters
		
	//Setters

	//Methods
	/**
	 * This method returns a list of all batches in the table
	 * @return a list of all batches in the table
	 */
	public List<Batch> getBatches() {
		return null;
	}
	
	/**
	 * This method attempts to add a batch
	 * @param batch the batch to be added
	 * @return true if the operation is successful, otherwise false
	 */
	public boolean add(Batch batch) {
		return false;
	}
	
	/**
	 * This method updates the specified batch
	 * @param batch the batch to be updated
	 * @return true if the operation is successful, otherwise false
	 */
	public boolean update(Batch batch) {
		return false;
	}
	
	/**
	 * This method removes the specified batch
	 * @param batch the batch to be removed
	 * @return true if the operation is successful, otherwise false
	 */
	public boolean remove(Batch batch) {
		return false;
	}
	
	/**
	 * This method gets a sample image from a specified project
	 * @param params an encapsulation of the user's credentials and a project id
	 * @return a url to a sample image
	 */
	public GetSampleImage_Result GetSampleImage(GetSampleImage_Params params) {
		return null;
	}
	
	/**
	 * This method downloads a batch for the user
	 * @param params an encapsulation of the user's credentials and a project id
	 * @return a complete batch from the specified project
	 */
	public DownloadBatch_Result DownloadBatch(DownloadBatch_Params params) {
		return null;
	}
	
	/**
	 * This method submits an indexed batch to the server
	 * @param params an encapsulation of the user's credentials, a batch id, and a list of field values
	 * @return success or failure based on the server's response
	 */
	public SubmitBatch_Result SubmitBatch(SubmitBatch_Params params) {
		return null;
	}
}