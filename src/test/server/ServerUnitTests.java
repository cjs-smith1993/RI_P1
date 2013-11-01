package test.server;

import org.junit.* ;

import client.communicator.DataImporter;
import server.database.Database;

public class ServerUnitTests {
	
	@Before
	public void setup() {
		DataImporter.getInstance().importFile("local_files/Records.xml");
	}
	
	@After
	public void teardown() {
		DataImporter.getInstance().importFile("local_files/Records.xml");
	}

	public static void main(String[] args) {
		
		String[] testClasses = new String[] {
				"test.server.BatchesTest", "test.server.DatabaseTest", "test.server.FieldsTest",
				"test.server.ProjectsTest", "test.server.UsersTest", "test.server.ValuesTest"
		};

		org.junit.runner.JUnitCore.main(testClasses);
	}
	
}

