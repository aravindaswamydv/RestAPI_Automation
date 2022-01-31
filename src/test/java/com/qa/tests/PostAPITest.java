package com.qa.tests;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.base.TestBase;
import com.qa.client.RestCleint;
import com.qa.data.Users;

public class PostAPITest extends TestBase{
	
	TestBase testBase;
	String serviceURL,apiURL,url;
	CloseableHttpResponse closeableHttpResponse;
	RestCleint restClient;
	
	@BeforeMethod
	public void setUP() {
	testBase = new TestBase();	
	serviceURL = prop.getProperty("URL");
	apiURL=prop.getProperty("serviceURL");
	
	url = serviceURL + apiURL;
	}
	
	@Test
	public void postAPITest() throws StreamWriteException, DatabindException, IOException {
		restClient=new RestCleint();
		HashMap<String,String> headerMap=new HashMap<String,String>();
		headerMap.put("Content-Type","application/json");
		
		// jackson API
		// Marshaling and un-marshaelling - convert java object to json object and vice versa
		
		ObjectMapper mapper = new ObjectMapper();
		
		Users users = new Users("morpheus","leader");  // expected users Object
		
		// object to json file
		mapper.writeValue(new File("C:\\eclipseWorkspace3\\Automation_RestApi\\src\\main\\java\\com\\qa\\data\\user.json"),users);
		
		// java Object to Json in String
		String userJsonString = mapper.writeValueAsString(users);
		System.out.println(userJsonString);
		
		closeableHttpResponse=restClient.post(url, userJsonString, headerMap);	
		

		// a. Status Code:
		int statusCode= closeableHttpResponse.getStatusLine().getStatusCode();
		System.out.println("http status code --> " + statusCode);
		
		
		Assert.assertEquals(statusCode,RESPONSE_STATUS_CODE_201,"Status Code is not 201");
		
		// JSON String
		String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
		
		JSONObject responseJson=new JSONObject(responseString);
		System.out.println("Response from API is --> " + responseJson);
		
		// Json to Java object
		Users userResObject = mapper.readValue(responseString, Users.class); // actual user Object
		System.out.println(userResObject);
		
		Assert.assertTrue(users.getName().equals(userResObject.getName()));
		
		Assert.assertTrue((users.getJob()).equals(userResObject.getJob()));
		System.out.println(userResObject.getId());
		System.out.println(userResObject.getCreatedAt());
	}

}
