package org.activeledger.java.sdk.activitystreams;

import java.util.ArrayList;
import java.util.List;

import org.activeledger.java.sdk.activeledgerjavasdk.ActiveledgerJavaSdkApplication;
import org.activeledger.java.sdk.generic.transaction.TransactionReq;
import org.activeledger.java.sdk.utility.Parsing;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ActivityStreams {

	private static final Logger logger = Logger.getLogger(TransactionReq.class);

	ObjectMapper mapper;
	Parsing parsing;
	final String streamURL = "http://%s:%s/api/stream/";
	final String txURL = "http://%s:%s/api/tx/";

	public ActivityStreams() {

		mapper = new ObjectMapper();
		parsing = new Parsing();
	}

	/*
	 * Send transaction to active ledger using http client input: transaction
	 * object
	 */
	public JSONArray getActivityStream(String host, String port, List<String> ids) {

		try {

			String url = String.format(streamURL, host, port);
			HttpClient httpclient = HttpClients.createDefault();
		

			HttpPost httppost = new HttpPost(url);
			GsonBuilder gsonBuilder = new GsonBuilder();
			Gson gson = gsonBuilder.create();
			String json = gson.toJson(ids);
			StringEntity entity = new StringEntity(json);
			entity.setContentType("application/json");
			httppost.setEntity(entity);

			HttpResponse response = httpclient.execute(httppost);
			String responseAsString = EntityUtils.toString(response.getEntity());

			JSONObject jsonObject = new JSONObject(responseAsString);

			return jsonObject.getJSONArray("streams");

		} catch (Exception e) {
			logger.error("Exception occurred while getting Activity Stream", e);
			throw new IllegalArgumentException("Exception occurred while getting activity streams:" + e.getMessage());
		}
	}

	public JSONObject getActivityStream(String host, String port, String id) {

		try {
			List<String> ids = new ArrayList<>();
			ids.add(id);
			JSONArray resp = this.getActivityStream(host, port, ids);

			if (!resp.get(0).equals("null"))// api returns null string when
											// requested id is not found
				return (JSONObject) resp.get(0);

			return null; // return null if id not found

		} catch (Exception e) {
			logger.error("Exception occurred while getting Activity Stream", e);
			throw new IllegalArgumentException("Exception occurred while getting activity stream:" + e.getMessage());
		}
	}

	public JSONObject getActivityStreamVolatile(String host, String port, String id) {

		try {

			String url = String.format(streamURL, host, port);
			HttpClient httpclient = HttpClients.createDefault();
			

			HttpGet httpGet = new HttpGet(url + id + "/volatile");

			HttpResponse response = httpclient.execute(httpGet);

			String responseAsString = EntityUtils.toString(response.getEntity());
			JSONObject jsonObject = new JSONObject(responseAsString);

			return jsonObject;

		} catch (Exception e) {
			logger.error("Exception occurred while getting node references", e);
			throw new IllegalArgumentException("Exception occurred while getting volatile activity stream"
					+ e.getMessage());
		}
	}
	
	
	public JSONObject setActivityStreamVolatile(String host, String port, String id,Object bdy) {

		try {

			String url = String.format(streamURL, host, port);
			HttpClient httpclient = HttpClients.createDefault();
		

			HttpPost httppost = new HttpPost(url+id+"/volatile");
			GsonBuilder gsonBuilder = new GsonBuilder();
			Gson gson = gsonBuilder.create();
			String json = gson.toJson(bdy);
			StringEntity entity = new StringEntity(json);
			entity.setContentType("application/json");
			httppost.setEntity(entity);

			HttpResponse response = httpclient.execute(httppost);
			
			String responseAsString = EntityUtils.toString(response.getEntity());

			JSONObject jsonObject = new JSONObject(responseAsString);
			return jsonObject;
		} catch (Exception e) {
			logger.error("Exception occurred while getting node references", e);
			throw new IllegalArgumentException("Exception occurred while getting volatile activity stream"
					+ e.getMessage());
		}
	}

	public JSONObject getActivityStreamChanges(String host, String port) {

		try {

			String url = String.format(streamURL, host, port);
			HttpClient httpclient = HttpClients.createDefault();
	

			HttpGet httpGet = new HttpGet(url + "changes");

			HttpResponse response = httpclient.execute(httpGet);

			String responseAsString = EntityUtils.toString(response.getEntity());
			JSONObject jsonObject = new JSONObject(responseAsString);

			return jsonObject;

		} catch (Exception e) {
			logger.error("Exception occurred while getting Activity Stream Changes", e);
			throw new IllegalArgumentException("Exception occurred while getting activity stream changes:" + e.getMessage());
		}
	}

	public JSONArray searchActivityStream(String host, String port, JSONObject query) {

		try {

			String url = String.format(streamURL, host, port);
			HttpClient httpclient = HttpClients.createDefault();
		

			HttpPost httppost = new HttpPost(url+"/search");
			GsonBuilder gsonBuilder = new GsonBuilder();
			Gson gson = gsonBuilder.create();
			String json = gson.toJson(query);
			StringEntity entity = new StringEntity(json);
			entity.setContentType("application/json");
			httppost.setEntity(entity);

			HttpResponse response = httpclient.execute(httppost);
			String responseAsString = EntityUtils.toString(response.getEntity());

			JSONObject jsonObject = new JSONObject(responseAsString);

			return jsonObject.getJSONArray("streams");

		} catch (Exception e) {
			logger.error("Exception occurred while getting Activity Stream", e);
			throw new IllegalArgumentException("Exception occurred while searching activity stream" + e.getMessage());
		}
	}

	public JSONArray searchActivityStream(String host, String port, String query) {

		try {

			String url = String.format(streamURL, host, port);
			HttpClient httpclient = HttpClients.createDefault();

			URIBuilder builder = new URIBuilder(url + "search");
			builder.setParameter("sql", query);

			HttpGet httpGet = new HttpGet(builder.build());

			HttpResponse response = httpclient.execute(httpGet);

			String responseAsString = EntityUtils.toString(response.getEntity());

			JSONObject jsonObject = new JSONObject(responseAsString);

			return jsonObject.getJSONArray("streams");

		} catch (Exception e) {
			logger.error("Exception occurred while getting Activity Stream Changes", e);
			throw new IllegalArgumentException("Exception occurred while searcing activity streams:" + e.getMessage());
		}
	}
	
	
	public JSONObject findTransaction(String host, String port, String umid) {

		try {

			String url = String.format(txURL, host, port);
			HttpClient httpclient = HttpClients.createDefault();

			HttpGet httpGet = new HttpGet(url+umid);

			HttpResponse response = httpclient.execute(httpGet);

			String responseAsString = EntityUtils.toString(response.getEntity());

			JSONObject jsonObject = new JSONObject(responseAsString);

			return jsonObject;

		} catch (Exception e) {
			logger.error("Exception occurred while getting Activity Stream Changes", e);
			throw new IllegalArgumentException("Exception occurred while finding transactions:" + e.getMessage());
		}
	}

}
