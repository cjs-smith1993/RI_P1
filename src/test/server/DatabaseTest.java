package test.server;

import org.junit.* ;
import server.database.Database;

public class DatabaseTest {
		
	@Before
	public void setup() {
	}
	
	@After
	public void teardown() {
	}

	@Test
	public void test_reset() {
		Database.getInstance().startTransaction();
		Database.getInstance().reset();
		assert Database.getInstance().batches().getBatches().size() == 0;
		assert Database.getInstance().fields().getFields(-1) == null;
		assert Database.getInstance().projects().getProjects().size() == 0;
		assert Database.getInstance().users().getUsers().size() == 0;
		assert Database.getInstance().values().getValues().size() == 0;
		Database.getInstance().endTransaction(true);
	}
}