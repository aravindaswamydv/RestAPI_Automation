package com.qa.tests;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.*;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.CDL;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.base.TestBase;
import com.qa.client.RestCleint;
import com.qa.utils.TestUtil;
import org.apache.commons.io.FileUtils;

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

//@Test(priority=1)
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
	System.out.println("Value of T -- " + tot);
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

//@Test(priority=2)
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


@Test(priority=1)
public void getAPITestWithHeaders1() throws ClientProtocolException, IOException {
	restClient = new RestCleint();
	HashMap<String,String> headerMap=new HashMap<String,String>();
	String responseString,jsonArrayString,usersJsonArrayString;
	headerMap.put("Content-Type","application/json");
	/*headerMap.put("username","testuser");
	headerMap.put("password","test1234");
	headerMap.put("Auth Token","12345");
	*/
	closeableHttpResponse=restClient.get(url,headerMap);
	
	// a. Status Code:
	int statusCode= closeableHttpResponse.getStatusLine().getStatusCode();
	System.out.println("http status code --> " + statusCode);
	
	
	Assert.assertEquals(statusCode,RESPONSE_STATUS_CODE_200,"Status Code is not 200");
	
	responseString = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
	
	// b. Json String: 
	JSONObject responseJson= new JSONObject(responseString);
	System.out.println("Response JSON from API -- " + responseJson);
	
	// Single value Assertion
	String perPageValue=TestUtil.getValuesByJPath(responseJson, "/per_page");
	System.out.println("Value of PerPage -- " + perPageValue);
	Assert.assertEquals(Integer.parseInt(perPageValue), 6);
	
	
	usersJsonArrayString=TestUtil.getValuesByJPath(responseJson, "data");
	System.out.println("data : " + usersJsonArrayString);
	
	JSONArray jsonArray = responseJson.getJSONArray("data");
	JSONArray sortedJonArray;
	
	// Storing the values of Jason Array in a csv file
	String targetCSVFilePath=TestUtil.writeJsonArrayToCSV("MyUsers2.csv", jsonArray);
	
	System.out.println("Data has been Sucessfully Written to "+ targetCSVFilePath);
	
	sortedJonArray=TestUtil.sortJasonArray(usersJsonArrayString, "last_name", "ASCENDING");
	
	targetCSVFilePath=TestUtil.writeJsonArrayToCSV("MyUsers3.csv", sortedJonArray);
	
	System.out.println("Data has been Sucessfully Written to "+ targetCSVFilePath);
	
	
	String sourceCSVFilePath = System.getProperty("user.dir")+"\\MyUsers2.csv";
	
	String resultCSVFilePath = TestUtil.createCSV("MyUsers4.csv");
	
	int differences = TestUtil.compareCSV(sourceCSVFilePath, targetCSVFilePath, resultCSVFilePath);
	
	Assert.assertTrue(differences<=0, "Differences found : " + differences);
	
	
	
}

public JSONArray sortJasonArray(String users) {
	//JSONArray jsonArr = new JSONArray(jsonArrStr);
	 String jsonArrStr = users;//"[ { \"ID\": \"135\", \"Name\": \"Fargo Chan\" },{ \"ID\": \"432\", \"Name\": \"Aaron Luke\" },{ \"ID\": \"252\", \"Name\": \"Dilip Singh\" }]";

	JSONArray jsonArr = new JSONArray(jsonArrStr);
    JSONArray sortedJsonArray = new JSONArray();
    List<JSONObject> jsonValues = new ArrayList<JSONObject>();
    
    for (int i = 0; i < jsonArr.length(); i++) {
        jsonValues.add(jsonArr.getJSONObject(i));
    }
    Collections.sort( jsonValues, new Comparator<JSONObject>() {
        //You can change "Name" with "ID" if you want to sort by ID
        private static final String KEY_NAME = "last_name";

        @Override
        public int compare(JSONObject a, JSONObject b) {
            String valA = new String();
            String valB = new String();

            try {
                valA = (String) a.get(KEY_NAME).toString();
                valB = (String) b.get(KEY_NAME).toString();
            } 
            catch (JSONException e) {
                //do something
            }

            return valA.compareTo(valB);
            //if you want to change the sort order, simply use the following:
            //return -valA.compareTo(valB);
        }
    });

   
    for (int i = 0; i < jsonArr.length(); i++) {
        sortedJsonArray.put(jsonValues.get(i));
    }
    
    return sortedJsonArray;
   /* System.out.println("Sorted Json Array");
    for (int i = 0; i < sortedJsonArray.length(); i++) {
    	System.out.println(sortedJsonArray.get(i).toString());
    }
    */
}


}
