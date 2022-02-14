package com.qa.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.json.CDL;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TestUtil {

	//public static JSONObject responseJson;

	public static String getValuesByJPath(JSONObject responseJson, String jPath) {
		Object obj = responseJson;
		for (String s : jPath.split("/"))
			if (!s.isEmpty())
				if (!(s.contains("[") || s.contains("]")))
					obj = ((JSONObject) obj).get(s);
				else if (s.contains("[") || s.contains("]"))
					obj = ((JSONArray) ((JSONObject) obj).get(s.split("\\[")[0]))
							.get(Integer.parseInt(s.split("\\[")[1].replace("]", "")));

		return obj.toString();
	}
	
	/**
	 * Create a file and save it with the values from JASON Array
	 * @param csvFilePath
	 * @param jsonArray
	 * @return path of the created file
	 */
	
	public static String writeJsonArrayToCSV(String csvFilePath,JSONArray jsonArray) {
		
		String fileLocation="";
		
		try {
			File file = new File(csvFilePath);
			String csv = CDL.toString(jsonArray);
			 FileUtils.writeStringToFile(file, csv, "ISO-8859-1");
			 System.out.println("Data has been Sucessfully Writeen to "+ file);
		     System.out.println(csv);
		     fileLocation=file.getAbsolutePath();
			}catch(Exception e) {
				e.printStackTrace();
			}
		return fileLocation;
	}
	
	/**
	 * Sort the Json Array by Ascending or Descending
	 * @param jsonArrStr - Json arry string
	 * @param keyName - Key Field Name in Json to be considered as a key for sorting 
	 * @param sortOrder - String values "ASC" for Asscending , "DESC" is for Descending
	 * @return - sorted JSON ARRY 
	 */
	public static JSONArray sortJasonArray(String jsonArrStr, String keyName, String sortOrder) {
		JSONArray jsonArr = new JSONArray(jsonArrStr);
		JSONArray sortedJsonArray = new JSONArray();
		List<JSONObject> jsonValues = new ArrayList<JSONObject>();

		for (int i = 0; i < jsonArr.length(); i++) {
			jsonValues.add(jsonArr.getJSONObject(i));
		}
		Collections.sort(jsonValues, new Comparator<JSONObject>() {
			private final String KEY_NAME = keyName;

			@Override
			public int compare(JSONObject a, JSONObject b) {
				String valA = new String();
				String valB = new String();

				try {
					valA = (String) a.get(KEY_NAME).toString();
					valB = (String) b.get(KEY_NAME).toString();
				} catch (JSONException e) {
					// do something
				}

				if (!sortOrder.toUpperCase().contains("DESC"))
					return valA.compareTo(valB);
				else
					return -valA.compareTo(valB);
			}
		});

		for (int i = 0; i < jsonArr.length(); i++) {
			sortedJsonArray.put(jsonValues.get(i));
		}

		return sortedJsonArray;
	}
	
	/**
	 * Compare the contents in 2 csv files and write the differences to resultant file
	 * @param sourceFilePath
	 * @param targetFilePath
	 * @param resultFilePath
	 * @return integer value representing the total differences found
	 * @throws IOException
	 */
	public static int compareCSV(String sourceFilePath, String targetFilePath, String resultFilePath) throws IOException {
		    
		    ArrayList<String> al1=new ArrayList<String>();
		    ArrayList<String> al2=new ArrayList<String>();
		    BufferedReader CSVFile1 = new BufferedReader(new FileReader(sourceFilePath));
		    String dataRow1 = CSVFile1.readLine();
		    while (dataRow1 != null)
		    {
		        String[] dataArray1 = dataRow1.split(",");
		        for (String item1:dataArray1)
		        { 
		           al1.add(item1);
		        }

		        dataRow1 = CSVFile1.readLine(); // Read next line of data.
		    }

		     CSVFile1.close();

		    BufferedReader CSVFile2 = new BufferedReader(new FileReader(targetFilePath));
		    String dataRow2 = CSVFile2.readLine();
		    while (dataRow2 != null)
		    {
		        String[] dataArray2 = dataRow2.split(",");
		        for (String item2:dataArray2)
		        { 
		           al2.add(item2);

		        }
		        dataRow2 = CSVFile2.readLine(); // Read next line of data.
		    }
		     CSVFile2.close();

		     int i=0;
		     for(String bs:al2)
		     {
		    	 System.out.println(al2.get(i++));
		         al1.remove(bs);
		     }

		     int size=al1.size();
		     System.out.println(size);

		     try
		        {
		            FileWriter writer=new FileWriter(resultFilePath);
		            while(size!=0)
		            {
		                size--;
		                writer.append(""+al1.get(size));
		                writer.append('\n');
		            }
		            writer.flush();
		            writer.close();
		        }
		        catch(IOException e)
		        {
		            e.printStackTrace();
		        }
		     
		     return al1.size();
	}
	
	/**
	 * Create a file and return its location.
	 * @param fileName - name of the file to be created
	 * @return location of file created
	 */
	public static String createCSV(String fileName) {
		
		String fileLocation="";
		
		try {
			File file = new File(fileName);
		     fileLocation=file.getAbsolutePath();
			}catch(Exception e) {
				e.printStackTrace();
			}
		return fileLocation;
	}

}
