package org.activeledger.java.sdk.utility;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import net.minidev.json.parser.ParseException;

public class Parsing {

	Map<String, String> map;
	public Parsing() {
		// TODO Auto-generated constructor stub
		 map= new HashMap<>();
	}
	
	//Parsing json object and returning a map of key value pairs.

	public  Map<String, String> parseJson(JSONObject jsonObject) throws ParseException {
		
		Set<String> set = jsonObject.keySet();
		Iterator<String> iterator = set.iterator();
		while (iterator.hasNext()) {
			Object obj = iterator.next();
			if (jsonObject.get(obj.toString()) instanceof JSONArray) {
				getArray(jsonObject.get(obj.toString()));
			} else {
				if (jsonObject.get(obj.toString()) instanceof JSONObject) {
					parseJson((JSONObject) jsonObject.get(obj.toString()));
				} else {

					map.put(obj.toString(), jsonObject.get(obj.toString()).toString());

				}
			}
		}

		return map;
	}
	public  void getArray(Object object2) throws ParseException {

		JSONArray jsonArr = (JSONArray) object2;

		for (int k = 0; k < jsonArr.length(); k++) {

			if (jsonArr.get(k) instanceof JSONObject) {
				parseJson((JSONObject) jsonArr.get(k));
			}

		}
	}
}
