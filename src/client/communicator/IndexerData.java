package client.communicator;

import java.util.ArrayList;
import org.w3c.dom.*;
import shared.model.*;

@Deprecated
public class IndexerData {

	private ArrayList<User> users = new ArrayList<User>();
	private ArrayList<Project> projects = new ArrayList<Project>();
	
	public IndexerData(Element root) {
		Element usersElement = (Element)root.getElementsByTagName("users").item(0);
		NodeList userElements = usersElement.getElementsByTagName("user");
		for (int i = 0; i < userElements.getLength(); i++) {
			//users.add(new User((Element)userElements.item(i)));
		}
		
		Element projectsElement = (Element)root.getElementsByTagName("projects").item(0);
		NodeList projectElements = projectsElement.getElementsByTagName("project");
		for (int i = 0; i < projectElements.getLength(); i++) {
			projects.add(new Project((Element)projectElements.item(i)));
		}
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("USERS\n");
		for (User user : users) {
			sb.append(user.toString());
		}
		return sb.toString();
	}

}
