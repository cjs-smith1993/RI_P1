package test.server;

import org.junit.* ;

public class ServerUnitTests {
	
	@Before
	public void setup() {
	}
	
	@After
	public void teardown() {
	}

	public static void main(String[] args) {
		
		String[] testClasses = new String[] {
				"test.server.BatchesTest", "test.server.DatabaseTest", "test.server.FieldsTest",
				"test.server.ProjectsTest", "test.server.UsersTest", "test.server.ValuesTest"
		};

		org.junit.runner.JUnitCore.main(testClasses);
	}
	
}

