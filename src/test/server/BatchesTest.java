package test.server;

import org.junit.* ;
import client.communicator.DataImporter;
import server.database.Database;
import shared.model.*;

public class BatchesTest {
		
	@Before
	public void setup() {
	}
	
	@After
	public void teardown() {
	}

	@Test
	public void test_add() {
		DataImporter.getInstance().importFile("demo/indexer_data/Records/Records.xml");
		Batch b = new Batch(111, "images/test", 4, -1, -1);
		Database.getInstance().startTransaction();
		Database.getInstance().batches().add(b);
		b.setId(61);
		assert Database.getInstance().batches().getBatch(61).toString().equals(b.toString());
		Database.getInstance().endTransaction(true);
	}
	
	@Test
	public void test_get() {
		Database.getInstance().startTransaction();
		Batch b = Database.getInstance().batches().getBatch(61);
		assert b.getId() == 61;
		assert b.getFile().equals("images/test");
		assert b.getProject_id() == 4;
		assert b.getUser_id() == -1;
		assert b.getState() == -1;
		Database.getInstance().endTransaction(true);
	}
	
	@Test
	public void test_setCompleted() {
		Database.getInstance().startTransaction();
		Database.getInstance().batches().setCompleted(1);
		Batch b = Database.getInstance().batches().getBatch(1);
		assert b.getState() == 1;
		Database.getInstance().endTransaction(true);
	}
}