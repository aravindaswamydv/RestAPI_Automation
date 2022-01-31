package com.qa.tests;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.base.TestBase;
import com.qa.client.RestCleint;
import com.qa.utils.TestUtil;

public class GetAPITest extends TestBase {
TestBase testBase;
RestCleint restClient;
String serviceURL,apiURL,url;
CloseableHttpResponse closeableHttpResponse;

@BeforeMethod
public void setUp() {
	testBase = new TestBase();
	serviceURL = prop.getProperty("URL");
	apiURL=prop.getProperty("serviceURL");
	
	url = serviceURL + apiURL;
	
}

@Test(priority=1)
public void getAPITestWithoutHeaders() throws ClientProtocolException, IOException {
	restClient = new RestCleint();
	closeableHttpResponse=restClient.get(url);
	
	// a. Status Code:
	int statusCode= closeableHttpResponse.getStatusLine().getStatusCode();
	System.out.println("http status code --> " + statusCode);
	
	
	Assert.assertEquals(statusCode,RESPONSE_STATUS_CODE_200,"Status Code is not 200");
	
	String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
	
	// b. Json String: 
	JSONObject responseJson= new JSONObject(responseString);
	System.out.println("Response JSON from API -- " + responseJson);
	
	// Single value Assertion
	String perPageValue=TestUtil.getValuesByJPath(responseJson, "/per_page");
	System.out.println("Value of PerPage -- " + perPageValue);
	Assert.assertEquals(Integer.parseInt(perPageValue), 6);
	
	String tot=TestUtil.getValuesByJPath(responseJson, "/total");
	System.out.println("Value of PerPage -- " + tot);
	Assert.assertEquals(Integer.parseInt(tot), 12);
	
	// get the value from JSON ARRAY
	
	String lastName=TestUtil.getValuesByJPath(responseJson, "/data[0]/last_name");
	String id=TestUtil.getValuesByJPath(responseJson, "/data[0]/id");
	String avatar=TestUtil.getValuesByJPath(responseJson, "/data[0]/avatar");
	String firstName=TestUtil.getValuesByJPath(responseJson, "/data[0]/first_name");
	
	System.out.println(lastName);
	System.out.println(id);
	System.out.println(avatar);
	System.out.println(firstName);
	

	// c. All Headers:
	Header[] headerArry = closeableHttpResponse.getAllHeaders();
	HashMap<String,String> allHeaders = new HashMap<String,String>();
	
	for(Header header:headerArry) {
		allHeaders.put(header.getName(), header.getValue());
	}
	System.out.print("Headers Array -- " + allHeaders);
}

@Test(priority=2)
public void getAPITestWithHeaders() throws ClientProtocolException, IOException {
	restClient = new RestCleint();
	HashMap<String,String> headerMap=new HashMap<String,String>();
	headerMap.put("Content-Type","application/json");
	/*headerMap.put("username","testuser");
	headerMap.put("password","test1234");
	headerMap.put("Auth Token","12345");*/
	
	closeableHttpResponse=restClient.get(url,headerMap);
	
	// a. Status Code:
	int statusCode= closeableHttpResponse.getStatusLine().getStatusCode();
	System.out.println("http status code --> " + statusCode);
	
	
	Assert.assertEquals(statusCode,RESPONSE_STATUS_CODE_200,"Status Code is not 200");
	
	String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
	
	// b. Json String: 
	JSONObject responseJson= new JSONObject(responseString);
	System.out.println("Response JSON from API -- " + responseJson);
	
	// Single value Assertion
	String perPageValue=TestUtil.getValuesByJPath(responseJson, "/per_page");
	System.out.println("Value of PerPage -- " + perPageValue);
	Assert.assertEquals(Integer.parseInt(perPageValue), 6);
	
	// get the value from JSON ARRAY
	
	String lastName=TestUtil.getValuesByJPath(responseJson, "/data[0]/last_name");
	String id=TestUtil.getValuesByJPath(responseJson, "/data[0]/id");
	String avatar=TestUtil.getValuesByJPath(responseJson, "/data[0]/avatar");
	String firstName=TestUtil.getValuesByJPath(responseJson, "/data[0]/first_name");
	
	System.out.println(lastName);
	System.out.println(id);
	System.out.println(avatar);
	System.out.println(firstName);
	
	// c. All Headers:
	Header[] headerArry = closeableHttpResponse.getAllHeaders();
	HashMap<String,String> allHeaders = new HashMap<String,String>();
	
	for(Header header:headerArry) {
		allHeaders.put(header.getName(), header.getValue());
	}
	System.out.print("Headers Array -- " + allHeaders);
}

}
