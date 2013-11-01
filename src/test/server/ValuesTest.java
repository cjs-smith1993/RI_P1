package test.server;

import org.junit.* ;
import client.communicator.DataImporter;
import server.database.Database;
import shared.model.*;

public class ValuesTest {
		
	@Before
	public void setup() {
	}
	
	@After
	public void teardown() {
	}

	@Test
	public void test_add() {
		Value v = new Value(-1, "test", 33, 55, 4);
		Database.getInstance().startTransaction();
		Database.getInstance().values().add(v);
		v.setId(561);
		assert Database.getInstance().values().getValue(561).toString().equals(v.toString());
		Database.getInstance().endTransaction(true);
	}
	
	@Test
	public void test_get() {
		Database.getInstance().startTransaction();
		Value v = Database.getInstance().values().getValue(561);
		assert v.getId() == 561;
		assert v.getRecord_value().equals("test");
		assert v.getRecord_num() == 33;
		assert v.getBatch_id() == 55;
		assert v.getField_num() == 4;
		Database.getInstance().endTransaction(true);
	}
	
}