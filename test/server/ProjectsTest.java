package test.server;

import org.junit.* ;
import client.communicator.DataImporter;
import server.database.Database;
import shared.model.*;

public class ProjectsTest {
		
	@Before
	public void setup() {
	}
	
	@After
	public void teardown() {
	}

	@Test
	public void test_add() {
		DataImporter.getInstance().importFile("demo/indexer_data/Records/Records.xml");
		Project p = new Project(-1, "test", 4, 11, 20);
		Database.getInstance().startTransaction();
		Database.getInstance().projects().add(p);
		p.setId(4);
		assert Database.getInstance().projects().getProject(4).toString().equals(p.toString());
		Database.getInstance().endTransaction(true);
	}
	
	@Test
	public void test_get() {
		Database.getInstance().startTransaction();
		Project p = Database.getInstance().projects().getProject(4);
		assert p.getId() == 4;
		assert p.getTitle().equals("test");
		assert p.getRecordsperimage() == 4;
		assert p.getFirstycoord() == 11;
		assert p.getRecordheight() == 20;
		Database.getInstance().endTransaction(true);
	}
	
}