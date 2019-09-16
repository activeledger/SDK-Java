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
package org.activeledger.java.sdk.onboard;

import java.math.BigInteger;

import org.activeledger.java.sdk.connection.Connection;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component("OnboardIdentityReq")
public class OnboardIdentityReq {

	private static final Logger logger = Logger.getLogger(OnboardIdentityReq.class);

	ObjectMapper mapper;

	public OnboardIdentityReq() {
		mapper = new ObjectMapper();
	}

	/*
	 * Sending the transaction to Activeledger
	 * input: OnboardingTransaction object
	 * output: JSONString containing the identity
	 */
	public String onBoardIdentity(OnboardTransaction transaction) {

		try {
			String transactionJson = mapper.writeValueAsString(transaction);
System.out.println("Transaction json onboard:\n"+transactionJson);
			HttpClient httpclient = HttpClients.createDefault();
			HttpPost httppost = new HttpPost(Connection.getConnectionURL());

			StringEntity entity = new StringEntity(transactionJson);
			entity.setContentType("application/json");
			httppost.setEntity(entity);
			HttpResponse response = httpclient.execute(httppost);

			String responseAsString = EntityUtils.toString(response.getEntity());
		
			return responseAsString;

		} catch (Exception e) {
			logger.error("Exception occurred while onboaring", e);
			throw new IllegalArgumentException("Exception occurred while onboaring:" + e.getMessage());
		}

	}

}
