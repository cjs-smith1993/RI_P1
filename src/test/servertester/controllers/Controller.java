package test.servertester.controllers;

import java.util.*;
import client.ClientException;
import client.communicator.ClientCommunicator;
import shared.communication.*;
import test.servertester.views.*;

public class Controller implements IController {

	private IView _view;
	
	public Controller() {
		return;
	}
	
	public IView getView() {
		return _view;
	}
	
	public void setView(IView value) {
		_view = value;
	}
	
	// IController methods
	//
	
	@Override
	public void initialize() {
		getView().setHost("localhost");
		getView().setPort("39640");
		operationSelected();
	}

	@Override
	public void operationSelected() {
		ArrayList<String> paramNames = new ArrayList<String>();
		paramNames.add("User");
		paramNames.add("Password");
		
		switch (getView().getOperation()) {
		case VALIDATE_USER:
			break;
		case GET_PROJECTS:
			break;
		case GET_SAMPLE_IMAGE:
			paramNames.add("Project");
			break;
		case DOWNLOAD_BATCH:
			paramNames.add("Project");
			break;
		case GET_FIELDS:
			paramNames.add("Project");
			break;
		case SUBMIT_BATCH:
			paramNames.add("Batch");
			paramNames.add("Record Values");
			break;
		case SEARCH:
			paramNames.add("Fields");
			paramNames.add("Search Values");
			break;
		default:
			assert false;
			break;
		}
		
		getView().setRequest("");
		getView().setResponse("");
		getView().setParameterNames(paramNames.toArray(new String[paramNames.size()]));
	}

	@Override
	public void executeOperation() {
		switch (getView().getOperation()) {
		case VALIDATE_USER:
			validateUser();
			break;
		case GET_PROJECTS:
			getProjects();
			break;
		case GET_SAMPLE_IMAGE:
			getSampleImage();
			break;
		case DOWNLOAD_BATCH:
			downloadBatch();
			break;
		case GET_FIELDS:
			getFields();
			break;
		case SUBMIT_BATCH:
			submitBatch();
			break;
		case SEARCH:
			search();
			break;
		default:
			assert false;
			break;
		}
	}
	
	private void validateUser() {
		String[] values = getView().getParameterValues();
		ValidateUser_Params params = new ValidateUser_Params(values[0], values[1]);
		getView().setRequest(params.toString());
		
		try {
			ClientCommunicator cc = ClientCommunicator.getInstance(getView().getHost(), Integer.parseInt(getView().getPort()));
			ValidateUser_Result response = cc.ValidateUser(params);
			getView().setResponse(response.toString());
		}
		catch (ClientException e) {
			getView().setResponse("FAILED\n");
			e.printStackTrace();
		}
	}
	
	private void getProjects() {
		String[] values = getView().getParameterValues();
		GetProjects_Params params = new GetProjects_Params(values[0], values[1]);
		getView().setRequest(params.toString());
		
		try {
			ClientCommunicator cc = ClientCommunicator.getInstance(getView().getHost(), Integer.parseInt(getView().getPort()));
			GetProjects_Result response = cc.GetProjects(params);
			getView().setResponse(response.toString());
		}
		catch (Exception e) {
			getView().setResponse("FAILED\n");
			e.printStackTrace();
		}
	}
	
	private void getSampleImage() {
		String[] values = getView().getParameterValues();
		GetSampleImage_Params params = new GetSampleImage_Params(values[0], values[1], Integer.parseInt(values[2]));
		getView().setRequest(params.toString());
		
		try {
			ClientCommunicator cc = ClientCommunicator.getInstance(getView().getHost(), Integer.parseInt(getView().getPort()));
			GetSampleImage_Result response = cc.GetSampleImage(params);
			getView().setResponse(response.toString());
		}
		catch (Exception e) {
			getView().setResponse("FAILED\n");
			e.printStackTrace();
		}
	}
	
	private void downloadBatch() {
		String[] values = getView().getParameterValues();
		DownloadBatch_Params params = new DownloadBatch_Params(values[0], values[1], Integer.parseInt(values[2]));
		getView().setRequest(params.toString());
		
		try {
			ClientCommunicator cc = ClientCommunicator.getInstance(getView().getHost(), Integer.parseInt(getView().getPort()));
			DownloadBatch_Result response = cc.DownloadBatch(params);
			getView().setResponse(response.toString());
		}
		catch (Exception e) {
			getView().setResponse("FAILED\n");
			e.printStackTrace();
		}
	}
	
	private void getFields() {
		String[] values = getView().getParameterValues();
		int project_id;
		if (values[2].equals(""))
			project_id = -1;
		else
			project_id = Integer.parseInt(values[2]);
		GetFields_Params params = new GetFields_Params(values[0], values[1], project_id);
		getView().setRequest(params.toString());
		
		try {
			ClientCommunicator cc = ClientCommunicator.getInstance(getView().getHost(), Integer.parseInt(getView().getPort()));
			GetFields_Result response = cc.GetFields(params);
			getView().setResponse(response.toString());
		}
		catch (Exception e) {
			getView().setResponse("FAILED\n");
			e.printStackTrace();
		}
	}
	
	private void submitBatch() {
		String[] values = getView().getParameterValues();
		ArrayList<ArrayList<String>> field_values = new ArrayList<ArrayList<String>>();
		String[] records_array = values[3].split(";");
		for (int i = 0; i < records_array.length; i++) {
			String[] values_array = records_array[i].split(",");
			field_values.add((ArrayList<String>) (Arrays.asList(values_array)));
		}
		
		SubmitBatch_Params params = new SubmitBatch_Params(values[0], values[1], Integer.parseInt(values[2]), field_values);
		getView().setRequest(params.toString());
		
		try {
			ClientCommunicator cc = ClientCommunicator.getInstance(getView().getHost(), Integer.parseInt(getView().getPort()));
			SubmitBatch_Result response = cc.SubmitBatch(params);
			getView().setResponse(response.toString());
		}
		catch (Exception e) {
			getView().setResponse("FAILED\n");
			e.printStackTrace();
		}
	}
	
	private void search() {
		String[] values = getView().getParameterValues();
		
		List<Integer> fields = new ArrayList<Integer>();
		String[] fields_array = values[2].split(",");
		for (int i = 0; i < fields_array.length; i++) {
			fields.add(Integer.parseInt(fields_array[i]));
		}
		
		List<String> search_values = new ArrayList<String>();
		String[] values_array = values[3].split(",");
		for (int i = 0; i < values_array.length; i++) {
			search_values.add(values_array[i]);
		}
		
		Search_Params params = new Search_Params(values[0], values[1], fields, search_values);
		getView().setRequest(params.toString());
		
		try {
			ClientCommunicator cc = ClientCommunicator.getInstance(getView().getHost(), Integer.parseInt(getView().getPort()));
			Search_Result response = cc.Search(params);
			getView().setResponse(response.toString());
		}
		catch (Exception e) {
			getView().setResponse("FAILED\n");
			e.printStackTrace();
		}
	}
}