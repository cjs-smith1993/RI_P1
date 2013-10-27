package shared.communication;

import java.util.List;
import shared.model.Project;

/**
 * This class is an encapsulation of the result of the GetProjects command, which allows for easier XML serialization/deserialization.
 * @author Connor Smith
 *
 */
public class GetProjects_Result {
//Fields
	/**
	 * The list of all available projects
	 */
	private List<Project> projects;

//Constructors
	/**
	 * This constructor is an encapsulation of the GetProjects results into a GetProjects_Result object
	 * @param projects the list of all available projects
	 */
	public GetProjects_Result(List<Project> projects) {
		super();
		this.projects = projects;
	}

//Getters
	/**
	 * This method returns the list of all available projects
	 * @return the projects in the database
	 */
	public List<Project> getProjects() {
		return projects;
	}
	
//Setters
	/**
	 * This method sets the list of all available projects
	 * @param projects the projects to set in the database
	 */
	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

//Methods
	/**
	 * This method returns information about all of the available projects.
	 * If the operation fails, an appropriate message is printed.
	 */
	@Override
	public String toString() {
		if (this.projects == null)
			return "FAILED\n";
		else {
			String result = "";
			for (Project project : this.projects) {
				result += project.getId() + "\n" + project.getTitle() + "\n";
			}
			return result;
		}
	}
}