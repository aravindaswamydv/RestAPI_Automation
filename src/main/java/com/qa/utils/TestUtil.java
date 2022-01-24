package com.qa.utils;

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

}
