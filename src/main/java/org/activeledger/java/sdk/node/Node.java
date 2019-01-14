/*
 * MIT License (MIT)
 * Copyright (c) 2018
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.activeledger.java.sdk.node;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.activeledger.java.sdk.connection.Connection;
import org.activeledger.java.sdk.generic.transaction.TransactionReq;
import org.activeledger.java.sdk.utility.Parsing;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Node {

	private static final Logger logger = Logger.getLogger(TransactionReq.class);

	ObjectMapper mapper;
	Parsing parsing;

	public Node() {
		mapper = new ObjectMapper();
		parsing=new Parsing();
	}

	/*
	 * Send transaction to active ledger using http client
	 * input: transaction object 
	 * 
	 */
	public List<String> getNodeReferences() {

		try {
			
			List<String> references=new ArrayList<>();
			//
			HttpClient httpclient = HttpClients.createDefault();
			HttpGet httpGet = new HttpGet(Connection.getConnectionURL()+"/a/status");
			
			HttpResponse response = httpclient.execute(httpGet);

			String responseAsString = EntityUtils.toString(response.getEntity());
			JSONObject jsonObject=new JSONObject(responseAsString);
			//JSOn objects received as part of response
			JSONObject neighbours=jsonObject.getJSONObject("neighbourhood").getJSONObject("neighbours");
		
			Iterator<String> refs=neighbours.keys();
			while(refs.hasNext())
			{
				String key=refs.next().toString();
			
				if(neighbours.getJSONObject(key).getBoolean("isHome"))
				{
					references.add(key);
				}
				
			}
			return references;

		} catch (Exception e) {
			logger.error("Exception occurred while getting node references", e);
			throw new IllegalArgumentException("Exception occurred while getting node references" + e.getMessage());
		}
	}
	
	public static void main(String []args)
	{
		Node n=new Node();
		n.getNodeReferences();
	}
}
