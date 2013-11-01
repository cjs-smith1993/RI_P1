package test.server;

import org.junit.* ;
import client.communicator.DataImporter;
import server.database.Database;
import shared.model.*;

public class UsersTest {
		
	@Before
	public void setup() {
	}
	
	@After
	public void teardown() {
	}

	@Test
	public void test_add() {
		User u = new User(-1, "user", "pass", "last", "first", "email@.com", 100, 633);
		Database.getInstance().startTransaction();
		Database.getInstance().users().add(u);
		u.setId(4);
		assert Database.getInstance().users().getUser(4).toString().equals(u.toString());
		Database.getInstance().endTransaction(true);
	}
	
	@Test
	public void test_get() {
		Database.getInstance().startTransaction();
		User u = Database.getInstance().users().getUser(4);
		assert u.getId() == 4;
		assert u.getUsername().equals("user");
		assert u.getPassword().equals("pass");
		assert u.getLastname().equals("last");
		assert u.getFirstname().equals("first");
		assert u.getEmail().equals("email@.com");
		assert u.getIndexedRecords() == 100;
		assert u.getBatch_id() == 633;
		Database.getInstance().endTransaction(true);
	}
	
}