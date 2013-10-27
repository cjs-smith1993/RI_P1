package client.communicator;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import server.database.Database;
import shared.model.*;

/**
 * This class handles the importing of data at the start of the program.
 * @author Connor Smith
 *
 */
public class DataImporter {

//Fields
	/**
	 * A singleton object for the data importer
	 */
	private static DataImporter instance = null;
	private static Database database = null;
	
//Constructors
	private DataImporter() {
		super();
	}
	
//Getters
	/**
	 * This method returns the instance of the data importer, or creates a new one if one doesn't exist
	 * @return The instance of the data importer
	 */
	public static DataImporter getInstance() {
		if (instance == null) {
			instance = new DataImporter();
			database = Database.getInstance();
		}
		return instance;
	}
	
	/**
	 * This method imports and parses an XML file and stores the resulting data in the database
	 * @param filename the path to the XML file
	 */
	public void importFile(String filename) {

		//Copy files to local directory
		File xmlFile = new File(filename);

		try {
			//Set up the document
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);
			doc.getDocumentElement().normalize();

			 //Parse the list of users
			NodeList users = doc.getElementsByTagName("user");
			parseUsers(users);

			//Parse the list of projects
			NodeList projects = doc.getElementsByTagName("project");
			parseProjects(projects);
		}
		catch (ParserConfigurationException | IOException | SAXException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method parses the list of users and stores the results in the database
	 * @param users The list of users
	 */
	public void parseUsers(NodeList users) {
		for (int i = 0; i < users.getLength(); i++) {
			//Create a user from the current element
			User user = new User((Element)users.item(i));
			System.out.println(user.toString());
			
			//Add the user to the database
			Database.getInstance().startTransaction();
			Database.getInstance().getAllUsers().add(user);
			Database.getInstance().endTransaction(true);
		}
	}

	/**
	 * This method parses the list of projects and stores the results in the database
	 * @param projects The list of projects
	 */
	public void parseProjects(NodeList projects) {
		for (int i = 0; i < projects.getLength(); i++) {
			//Create a project from the current element
			Element curProject = (Element)projects.item(i);
			Project project = new Project(curProject);
			System.out.println(project.toString());
			
			//Database.getInstance().getAllProjects().add(project); //Add the project to the database
			
			//Parse the list of fields for the project
			Element fields = (Element)curProject.getElementsByTagName("fields").item(0);
			parseFields(fields.getElementsByTagName("field"), i);
			
			//Parse the list of batches for the project
			Element batches = (Element)curProject.getElementsByTagName("images").item(0);
			parseBatches(batches.getElementsByTagName("image"), i);
		}
	}
	
	/**
	 * This method parses the list of fields and stores the results in the database
	 * @param fields The list of fields
	 * @param project_id the id of the project these fields are associated with
	 */
	public void parseFields(NodeList fields, int project_id) {
		for (int i = 0; i < fields.getLength(); i++) {
			//Create a field from the current element
			Field field = new Field((Element)fields.item(i), i, project_id);
			System.out.println(field);
			
			//Add the field to the database
			//Database.getInstance().getAllFields().add(field);
		}
	}
	
	/**
	 * This method parses the list of batches and stores the results in the database
	 * @param batches The list of batches
	 */
	public void parseBatches(NodeList batches, int project_id) {
		for (int i = 0; i < batches.getLength(); i++) {
			//Create a batch from the current element
			Element curBatch = (Element)batches.item(i);
			Batch batch = new Batch(curBatch, project_id);
			System.out.println(batch.toString());
			
			//Add the batch to the database
			//Database.getInstance().getAllBatches().add(batch);
			
			//Parse the list of records in the batch
			Element records = (Element)curBatch.getElementsByTagName("records").item(0);
			if (records != null)
				parseRecords(records.getElementsByTagName("record"), i);
		}
	}
	
	/**
	 * This method parses the list of records and stores the results in the database
	 * @param records The list of records
	 */
	public void parseRecords(NodeList records, int batch_id) {
		for (int i = 0; i < records.getLength(); i++) {
			//Get the record tag, then the values tag, then the list of values
			Element curRecord = (Element)records.item(i);
			Element valuesTag = (Element)curRecord.getElementsByTagName("values").item(0);
			NodeList values = valuesTag.getElementsByTagName("value");
						
			for (int j = 0; j < values.getLength(); j++) {
				//Create a value for each value
				Element curValue = (Element)values.item(j);
				Value value = new Value(curValue, i, j, batch_id);
				System.out.println(value.toString());
				
				//Add the value to the database
				//Database.getInstance().getRecords().add(value);
			}
		}
	}
	
//Main
	/**
	 * This main routine creates an instance of a data importer and imports data from an XML file
	 * @param args
	 */
	public static void main(String args[]) {
		DataImporter di = getInstance();
		di.importFile("demo/indexer_data/Records/Records.xml");
	}
}