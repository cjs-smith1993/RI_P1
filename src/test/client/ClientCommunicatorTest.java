package test.client;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import org.junit.*;
import server.database.Database;
import shared.communication.*;
import shared.model.*;
import client.ClientException;
import client.communicator.*;

public class ClientCommunicatorTest {
	
	@Before
	public void setup() {
	}
	
	@After
	public void teardown() {
	}
	
	@Test
	public void test_setUp() {		
		ClientCommunicator cc = ClientCommunicator.getInstance("localhost", 8080);
		assert ClientCommunicator.getServerHost().equals("localhost");
		assert ClientCommunicator.getServerPort() == 8080;
		assert cc.getXmlStream() != null;
	}
	
	@Test
	public void test_doGet() {
		ClientCommunicator cc = ClientCommunicator.getInstance("localhost", 8080);
		URL url;
		try {
			url = new URL(ClientCommunicator.getUrlPrefix() + "/Users/CJS/Documents/School/Fall2013/CS_240/workspace/RecordIndexer/images/1890_image0.png");
			cc.doGet(url);
			File file = new File("/Users/CJS/Documents/School/Fall2013/CS_240/workspace/RecordIndexer/local_files/1890_image0.png");
			assert file.exists();
		}
		catch (ClientException | IOException e) {
			e.printStackTrace();
			assert false;
		}
	}
	
	@Test
	public void test_ValidateUser() {
		ClientCommunicator cc = ClientCommunicator.getInstance("localhost", 8080);
		ValidateUser_Params params = new ValidateUser_Params("sheila", "parker");
		try {
			assert cc.ValidateUser(params).toString().equals("TRUE\nSheila\nParker\n0\n");
			params = new ValidateUser_Params("sheila", "parker2");
			assert cc.ValidateUser(params).toString().equals("FALSE\n");
		}
		catch (ClientException e) {
			e.printStackTrace();
			assert false;
		}
	}
	
	@Test
	public void test_GetProjects() {
		ClientCommunicator cc = ClientCommunicator.getInstance("localhost", 8080);
		GetProjects_Params params = new GetProjects_Params("sheila", "parker");
		try {
			assert cc.GetProjects(params).toString().equals("1\n1890 Census\n2\n1900 Census\n3\nDraft Records\n");
			params = new GetProjects_Params("sheila", "parker2");
			assert cc.GetProjects(params).toString().equals("FAILED\n");
		}
		catch (ClientException e) {
			e.printStackTrace();
			assert false;
		}
	}
	
	@Test
	public void test_GetSampleImage() {
		ClientCommunicator cc = ClientCommunicator.getInstance("localhost", 8080);
		GetSampleImage_Params params = new GetSampleImage_Params("sheila", "parker", 1);
		try {
			assert cc.GetSampleImage(params).toString().equals("images/1890_image0.png\n");
			params = new GetSampleImage_Params("sheila", "parker2", 1);
			assert cc.GetSampleImage(params).toString().equals("FAILED\n");
			params = new GetSampleImage_Params("sheila", "parker", 0);
			assert cc.GetSampleImage(params).toString().equals("FAILED\n");
		}
		catch (ClientException e) {
			e.printStackTrace();
			assert false;
		}
	}
	
	@Test
	public void test_DownloadBatch() {
		ClientCommunicator cc = ClientCommunicator.getInstance("localhost", 8080);
		DownloadBatch_Params params = new DownloadBatch_Params("sheila", "parker", 11);
		try {
			DataImporter.getInstance().importFile("demo/indexer_data/Records/Records.xml");
			assert cc.DownloadBatch(params).toString().equals("FAILED\n");
			
			params = new DownloadBatch_Params("sheila", "parker", 1);
			cc.DownloadBatch(params);
			Database.getInstance().startTransaction();
			Batch b = Database.getInstance().batches().getBatch(1);
			Database.getInstance().endTransaction(true);
			//System.out.println(b.getUser_id());
			//System.out.println(b.getState());
			assert b.getUser_id() == 3;
			assert b.getState() == 0;
			
			Database.getInstance().startTransaction();
			User u = Database.getInstance().users().getUser(3);
			Database.getInstance().endTransaction(true);
			assert u.getBatch_id() == 1;
			
			params = new DownloadBatch_Params("sheila", "parker2", 1);
			assert cc.DownloadBatch(params).toString().equals("FAILED\n");
			params = new DownloadBatch_Params("sheila", "parker", 2);
			assert cc.DownloadBatch(params).toString().equals("FAILED\n");
		}
		catch (ClientException e) {
			e.printStackTrace();
			assert false;
		}
	}
	
	@Test
	public void test_SubmitBatch() {
		ClientCommunicator cc = ClientCommunicator.getInstance("localhost", 8080);
		ArrayList<ArrayList<String>> values = new ArrayList<ArrayList<String>>();
		for (int i = 0; i < 8; i++)
			values.add(new ArrayList<String>());
		values.get(0).add(0, "1");
		values.get(0).add(1, "2");
		values.get(0).add(2, "3");
		values.get(0).add(3, "4");
		values.get(1).add(0, "5");
		values.get(1).add(1, "6");
		values.get(1).add(2, "7");
		values.get(1).add(3, "8");
		values.get(2).add(0, "9");
		values.get(2).add(1, "10");
		values.get(2).add(2, "11");
		values.get(2).add(3, "12");
		values.get(3).add(0, "13");
		values.get(3).add(1, "14");
		values.get(3).add(2, "15");
		values.get(3).add(3, "16");
		values.get(4).add(0, "17");
		values.get(4).add(1, "18");
		values.get(4).add(2, "19");
		values.get(4).add(3, "20");
		values.get(5).add(0, "21");
		values.get(5).add(1, "22");
		values.get(5).add(2, "23");
		values.get(5).add(3, "24");
		values.get(6).add(0, "25");
		values.get(6).add(1, "26");
		values.get(6).add(2, "27");
		values.get(6).add(3, "28");
		values.get(7).add(0, "29");
		values.get(7).add(1, "31");
		values.get(7).add(2, "31");
		values.get(7).add(3, "32");
		
		
		SubmitBatch_Params params = new SubmitBatch_Params("sheila", "parker", 112, values);
		try {
			assert cc.SubmitBatch(params).toString().equals("FAILED\n");
			
			params = new SubmitBatch_Params("sheila", "parker", 1, values);
			cc.SubmitBatch(params);
			Database.getInstance().startTransaction();
			Batch b = Database.getInstance().batches().getBatch(41);
			Database.getInstance().endTransaction(true);
			assert b.getState() == 1;
			assert b.getUser_id() == -1;
			
			params = new SubmitBatch_Params("sheila", "parker", 1, values);
			//System.out.println(cc.SubmitBatch(params).toString());
			assert cc.SubmitBatch(params).toString().equals("FAILED\n");
		}
		catch (ClientException e) {
			e.printStackTrace();
			assert false;
		}
	}
	
	@Test
	public void test_GetFields() {
		ClientCommunicator cc = ClientCommunicator.getInstance("localhost", 8080);
		GetFields_Params params = new GetFields_Params("sheila", "parker", 1);
		try {
			assert cc.GetFields(params).toString().equals("1\n1\nLast Name\n1\n2\nFirst Name\n1\n3\nGender\n1\n4\nAge\n");
			params = new GetFields_Params("sheila", "parker2", 2);
			assert cc.GetFields(params).toString().equals("FAILED\n");
			params = new GetFields_Params("sheila", "parker", 12);
			assert cc.GetFields(params).toString().equals("FAILED\n");
		}
		catch (ClientException e) {
			e.printStackTrace();
			assert false;
		}
	}
	
	@Test
	public void test_Search() {
		ClientCommunicator cc = ClientCommunicator.getInstance("localhost", 8080);
		ArrayList<Integer> fields = new ArrayList<Integer>();
		fields.add(10);
		ArrayList<String> search_values = new ArrayList<String>();
		search_values.add("FOX");
		search_values.add("bartlett");
		
		Search_Params params = new Search_Params("sheila", "parker", fields, search_values);
		try {
			//System.out.println(cc.Search(params).toString());
			assert cc.Search(params).toString().equals("41\nimages/draft_image0.png\n1\n10\n41\nimages/draft_image0.png\n2\n10\n");
			fields.remove(0);
			fields.add(1);
			params = new Search_Params("sheila", "parker", fields, search_values);
			assert cc.Search(params).toString().equals("FAILED\n");
		}
		catch (ClientException e) {
			e.printStackTrace();
			assert false;
		}
	}
}