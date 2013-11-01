package test.server;

import org.junit.* ;
import client.communicator.DataImporter;
import server.database.Database;
import shared.model.*;

public class FieldsTest {
		
	@Before
	public void setup() {
	}
	
	@After
	public void teardown() {
	}

	@Test
	public void test_add() {
		DataImporter.getInstance().importFile("demo/indexer_data/Records/Records.xml");
		Field f = new Field(-1, "tests", 111, 66, "fieldhelp/test",
				"knowndata/test", 12, 4);
		Database.getInstance().startTransaction();
		Database.getInstance().fields().add(f);
		f.setId(14);
		assert Database.getInstance().fields().getField(14).toString().equals(f.toString());
		Database.getInstance().endTransaction(true);
	}
	
	@Test
	public void test_get() {
		Database.getInstance().startTransaction();
		Field f = Database.getInstance().fields().getField(14);
		assert f.getId() == 14;
		assert f.getTitle().equals("tests");
		assert f.getXcoord() == 111;
		assert f.getWidth() == 66;
		assert f.getHelphtml().equals("fieldhelp/test");
		assert f.getKnowndata().equals("knowndata/test");
		assert f.getField_num() == 12;
		assert f.getProject_id() == 4;
		Database.getInstance().endTransaction(true);
	}
}