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
 * This class is a representation of the projects table in the database
 * @author Connor Smith
 *
 */
public class Projects {
	
	/**
	 * This method returns a list of all projects in the table
	 * @return a list of all projects in the table
	 */
	public List<Project> getProjects() {
		List<Project> projects = new ArrayList<Project>();
		Connection connection = Database.getConnection();
		PreparedStatement prepstatement = null;
		ResultSet results = null;
		
		try {
			//Get the list of all projects in the database
			String getsql = "SELECT * FROM projects";
			prepstatement = connection.prepareStatement(getsql);
			results = prepstatement.executeQuery();
			
			//Add each project to the list
			while (results.next()) {
				int id = results.getInt(1);
				String title = results.getString(2);
				int recordsperimage = results.getInt(3);
				int firstycoord = results.getInt(4);
				int recordheight = results.getInt(5);
				
				projects.add(new Project(id, title, recordsperimage, firstycoord, recordheight));
			}

		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return projects;
	}
	
	/**
	 * This method gets the project matching the project_id
	 * @param project_id the id of the project to get
	 * @return the matching project, or null if one doesn't exist
	 */
	public Project getProject(int project_id) {
		Project project = null;
		PreparedStatement prepstatement = null;
		ResultSet results = null;

		try {
			String getsql = "SELECT * FROM projects WHERE id = ?";
			prepstatement = Database.getConnection().prepareStatement(getsql);
			prepstatement.setInt(1, project_id);
			results = prepstatement.executeQuery();
			//If there isn't a matching project, quit
			if (!results.isBeforeFirst())
				return null;

			String title = results.getString(2);
			int recordsperimage = results.getInt(3);
			int firstycoord = results.getInt(4);
			int recordheight = results.getInt(5);
			project = new Project(project_id, title, recordsperimage, firstycoord, recordheight);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return project;
	}
	
	/**
	 * This method attempts to add a project
	 * @param project the project to be added
	 * @return the id of the project added, or -1 if the insert failed
	 */
	public int add(Project project) {
		Connection connection = Database.getConnection();
		PreparedStatement prepstatement = null;
		Statement statement = null;
		ResultSet results = null;
		int projectid = -1;
		
		try {
			//Set up the SQL statement
			String addsql = "INSERT INTO projects (title, recordsperimage, firstycoord, recordheight)"
							+ "VALUES (?,?,?,?)";
			
			//Fill unknowns with the project's information
			prepstatement = connection.prepareStatement(addsql);			
			prepstatement.setString(1, project.getTitle());
			prepstatement.setInt(2, project.getRecordsperimage());
			prepstatement.setInt(3, project.getFirstycoord());
			prepstatement.setInt(4, project.getRecordheight());
						
			//If the project was added correctly
			if (prepstatement.executeUpdate() == 1) {
				//Set the project's id to the next id in the table
				statement = connection.createStatement();
				results = statement.executeQuery("SELECT last_insert_rowid()");
				results.next();
				projectid = results.getInt(1);
				project.setId(projectid);
			}
			else {
				System.out.println("ERROR: Insert failed.");
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return projectid;
	}
	
	/**
	 * This method updates the specified project
	 * @param project the project to be updated
	 * @return true if the operation is successful, otherwise false
	 */
	public boolean update(Project project) {
		return false;
	}
	
	/**
	 * This method removes the specified project
	 * @param project the project to be removed
	 * @return true if the operation is successful, otherwise false
	 */
	public boolean remove(Project project) {
		return false;
	}

	public GetProjects_Result GetProjects_Result(GetProjects_Params params) {
		return new GetProjects_Result(getProjects());
	}
}