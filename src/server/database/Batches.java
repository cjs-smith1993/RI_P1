package server.database;

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
				String file_name = results.getString(2);
				int project_id = results.getInt(3);
				int user_id = results.getInt(4);
				int status = results.getInt(5);
				
				batches.add(new Batch(id, file_name, project_id, user_id, status));
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
	 * This method gets the batch matching the batch_id
	 * @param batch_id the id of the batch to get
	 * @return the matching batch, or null if one doesn't exist
	 */
	public Batch getBatch(int batch_id) {
		Batch batch = null;
		
		Connection connection = Database.getConnection();
		PreparedStatement prepstatement = null;
		ResultSet results = null;
		
		try {
			//Get the batch with the matching id from the database
			String getsql = "SELECT * FROM batches WHERE id = ?";
			prepstatement = connection.prepareStatement(getsql);
			prepstatement.setInt(1, batch_id);
			results = prepstatement.executeQuery();
			//If there isn't a matching batch, quit
			if (!results.isBeforeFirst())
				return null;
			
			//Return the matching batch
			String file_name = results.getString(2);
			int project_id = results.getInt(3);
			int user_id = results.getInt(4);
			int status = results.getInt(5);
			batch = new Batch(batch_id, file_name, project_id, user_id, status);
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
		return batch;
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
		int batch_id = -1;
		
		try {
			//Set up the SQL statement
			String addsql = "INSERT INTO batches (file_name, project_id, user_id, status)"
							+ "VALUES (?,?,?,?)";
			
			//Fill unknowns with the batch's information
			prepstatement = connection.prepareStatement(addsql);
			prepstatement.setString(1, batch.getFile());
			prepstatement.setInt(2, batch.getProject_id());
			prepstatement.setInt(3, batch.getUser_id());
			prepstatement.setInt(4, batch.getState());
						
			//If the batch was added correctly
			if (prepstatement.executeUpdate() == 1) {
				//Set the batch's id to the next id in the table
				statement = connection.createStatement();
				results = statement.executeQuery("SELECT last_insert_rowid()");
				results.next();
				batch_id = results.getInt(1);
				batch.setId(batch_id);
			}
			else {
				System.out.println("ERROR: Insert failed.");
			}
		}
		catch (SQLException e) {
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
		return batch_id;
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
	 * This method sets the specified batch as completed
	 * @param batch_id the batch to set as completed
	 */
	public void setCompleted(int batch_id) {
		PreparedStatement prepstatement = null;
		
		try {
			//Set the specified batch's state to completed
			String updatesql = "UPDATE batches SET status = 1 WHERE id = ?";
			prepstatement = Database.getConnection().prepareStatement(updatesql);
			prepstatement.setInt(1, batch_id);
			prepstatement.executeUpdate();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				if (prepstatement != null)
					prepstatement.close();
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * This method gets a sample image from a specified project
	 * @param params an encapsulation of the user's credentials and a project id
	 * @return a url to a sample image
	 */
	public GetSampleImage_Result GetSampleImage(GetSampleImage_Params params) {
		int project_id = params.getProject_id();
		PreparedStatement prepstatement = null;
		ResultSet results = null;
		
		try {
			//Get the URL of the first batch belonging to the desired project
			String getsql = "SELECT file_name FROM batches WHERE project_id = ? LIMIT 1";
			prepstatement = Database.getConnection().prepareStatement(getsql);
			prepstatement.setInt(1, project_id);
			results = prepstatement.executeQuery();
			//If there is no matching URL for this project, return nothing
			if (!results.isBeforeFirst())
				return new GetSampleImage_Result(null);
			//Return the matching URL or null if there isn't one
			return new GetSampleImage_Result(results.getString(1));
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
		return new GetSampleImage_Result(null);
	}
	
	/**
	 * This method downloads a batch for the user
	 * @param params an encapsulation of the user's credentials and a project id
	 * @return a complete batch from the specified project
	 */
	public DownloadBatch_Result DownloadBatch(DownloadBatch_Params params) {
		int project_id = params.getProject_id();
		DownloadBatch_Result result = new DownloadBatch_Result(-1, project_id, "", -1, -1, -1, -1, null);

		PreparedStatement prepstatement = null;
		String updatesql;
		
		//First get the user's id
		int user_id = -1;
		List<User> users = Database.getInstance().users().getUsers();
		for (User user : users) {
			if (user.getUsername().equals(params.getUsername()))
				user_id = user.getId();
		}
		
		try {
			//If the user already has a batch, quit
			User user = Database.getInstance().users().getUser(user_id);
			if (user.getBatch_id() != -1)
				return result;
			
			//Get the project matching the requested id. If there isn't a matching project, quit
			Project project = Database.getInstance().projects().getProject(project_id);
			if (project == null)
				return result;
			result.setProject_id(project.getId());
			result.setFirst_y_coord(project.getFirstycoord());
			result.setRecord_height(project.getRecordheight());
			result.setNum_records(project.getRecordsperimage());
			
			//Get the first batch that is available
			List<Batch> batches = Database.getInstance().batches().getBatches();
			for (Batch batch : batches) {
				//Only select a batch if it's in the correct project, hasn't been completed, and isn't currently checked out
				if (batch.getProject_id() == project_id && batch.getState() != 1 && batch.getUser_id() == -1) {
					result.setBatch_id(batch.getId());
					result.setImage_url(batch.getFile());
					break;
				}
			}
			//If there was no available batch in the project, quit
			if (result.getBatch_id() == -1)
				return result;
			
			//Get the fields corresponding to the project
			List<Field> fields = Database.getInstance().fields().getFields(project_id);
			result.setNum_fields(fields.size());
			result.setFields(fields);
						
			//Mark the batch as in use by the correct user
			updatesql = "UPDATE batches SET user_id = ?, status = 0 WHERE id = ?";
			prepstatement = Database.getConnection().prepareStatement(updatesql);
			prepstatement.setInt(1, user_id);
			prepstatement.setInt(2, result.getBatch_id());
			prepstatement.executeUpdate();
			
			//Mark the user as using the batch
			updatesql = "UPDATE users SET batch_id = ? WHERE id = ?";
			prepstatement = Database.getConnection().prepareStatement(updatesql);
			prepstatement.setInt(1, result.getBatch_id());
			prepstatement.setInt(2, user_id);
			prepstatement.executeUpdate();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				if (prepstatement != null)
					prepstatement.close();
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	/**
	 * This method submits an indexed batch to the server
	 * @param params an encapsulation of the user's credentials, a batch id, and a list of field values
	 * @return success or failure based on the server's response
	 */
	public SubmitBatch_Result SubmitBatch(SubmitBatch_Params params) {
		int batch_id = params.getBatch_id();
		SubmitBatch_Result result = new SubmitBatch_Result(false);
		
		Connection connection = Database.getConnection();
		PreparedStatement prepstatement = null;
		String updatesql;
		String addsql;
		
		//First get the user's id
		int user_id = -1;
		List<User> users = Database.getInstance().users().getUsers();
		for (User user : users) {
			if (user.getUsername().equals(params.getUsername()))
				user_id = user.getId();
		}	
		
		try {
			//Get the batch matching the requested id. If there is no matching batch or it is not checked out to the user, quit.
			Batch batch = Database.getInstance().batches().getBatch(batch_id);
			if (batch == null || user_id != batch.getUser_id())
				return result;	

			//Get the project the batch belongs to
			Project project = Database.getInstance().projects().getProject(batch.getProject_id());
			int recordsperimage = project.getRecordsperimage();
			
			//Update the batch
			ArrayList<ArrayList<String>> field_values = params.getField_values();
			int num_fields = Database.getInstance().fields().getFields(project.getId()).size();
			//If there are an incorrect number of records in the submitted batch, quit
			if (field_values.size() != recordsperimage)
				return result;
			//If there aren't enough values per record in the submitted batch, quit
			for (List<String> record : field_values) {
				if (record.size() != num_fields)
					return result;
			}
			
			for (int i = 0; i < field_values.size(); i++) {
				for (int j = 0; j < field_values.get(i).size(); j++) {
					addsql = "INSERT INTO record_values (record_value, record_num, field_num, batch_id) VALUES (?,?,?,?)";
					prepstatement = connection.prepareStatement(addsql);
					prepstatement.setString(1, field_values.get(i).get(j));
					prepstatement.setInt(2, i+1);
					prepstatement.setInt(3, j+1);
					prepstatement.setInt(4, batch_id);
					prepstatement.executeUpdate();
				}
			}
				
			//Mark the batch as no longer in use and completed
			updatesql = "UPDATE batches SET user_id = ?, status = ? WHERE user_id = ?";
			prepstatement = connection.prepareStatement(updatesql);
			prepstatement.setInt(1, -1);
			prepstatement.setInt(2, 1);
			prepstatement.setInt(3, user_id);
			prepstatement.executeUpdate();
			
			//Mark the user as no longer using the batch and increment the number of indexed records
			User u = Database.getInstance().users().getUser(user_id);
			int cur_indexed_records = u.getIndexedRecords();
			updatesql = "UPDATE users SET batch_id = ?, indexed_records = ? WHERE id = ?";
			prepstatement = connection.prepareStatement(updatesql);
			prepstatement.setInt(1, -1);
			prepstatement.setInt(2, cur_indexed_records+recordsperimage);
			prepstatement.setInt(3, user_id);
			prepstatement.executeUpdate();
			
			result.setSuccess(true);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				if (prepstatement != null)
					prepstatement.close();
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
}