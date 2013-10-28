package server.database;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import shared.communication.*;
import shared.model.*;

/**
 * This class is a representation of the batches table in the database
 * @author Connor Smith
 *
 */
public class Batches {

	/**
	 * This method returns a list of all batches in the table
	 * @return a list of all batches in the table
	 */
	public List<Batch> getBatches() {
		List<Batch> batches = new ArrayList<Batch>();
		PreparedStatement prepstatement = null;
		ResultSet results = null;
		
		try {
			//Get the list of all batches in the database
			String getsql = "SELECT * FROM batches";
			prepstatement = Database.getConnection().prepareStatement(getsql);
			results = prepstatement.executeQuery();
			
			//Add each batch to the list
			while (results.next()) {
				int id = results.getInt(1);
				File file = new File(results.getString(2));
				int project_id = results.getInt(3);
				int user_id = results.getInt(4);
				int status = results.getInt(5);
				
				batches.add(new Batch(id, file, project_id, user_id, status));
			}

		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				if (prepstatement != null)
					prepstatement.close();
				if (results != null)
					results.close();
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return batches;
	}
	
	/**
	 * This method attempts to add a batch
	 * @param batch the batch to be added
	 * @return the id of the batch added, or -1 if the insert failed
	 */
	public int add(Batch batch) {
		Connection connection = Database.getConnection();
		PreparedStatement prepstatement = null;
		Statement statement = null;
		ResultSet results = null;
		int batchid = -1;
		
		try {
			//Set up the SQL statement
			String addsql = "INSERT INTO batches (file, project_id, user_id, status)"
							+ "VALUES (?,?,?,?)";
			
			//Fill unknowns with the batch's information
			prepstatement = connection.prepareStatement(addsql);			
			prepstatement.setString(1, batch.getFile().getCanonicalPath());
			prepstatement.setInt(2, batch.getProject_id());
			prepstatement.setInt(3, batch.getUser_id());
			prepstatement.setInt(4, batch.getState());
						
			//If the batch was added correctly
			if (prepstatement.executeUpdate() == 1) {
				//Set the batch's id to the next id in the table
				statement = connection.createStatement();
				results = statement.executeQuery("SELECT last_insert_rowid()");
				results.next();
				batchid = results.getInt(1);
				batch.setId(batchid);
			}
			else {
				System.out.println("ERROR: Insert failed.");
			}
		}
		catch (SQLException | IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				if (prepstatement != null)
					prepstatement.close();
				if (statement != null)
					statement.close();
				if (results != null)
					results.close();
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return batchid;
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