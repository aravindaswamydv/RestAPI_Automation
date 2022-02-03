package com.qa.utils;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.json.CDL;
import org.json.JSONArray;
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

}
