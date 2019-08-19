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

import java.security.KeyPair;
import java.util.HashMap;
import java.util.Map;

import org.activeledger.java.sdk.activeledgerjavasdk.ActiveledgerJavaSdkApplication;
import org.activeledger.java.sdk.generic.transaction.TxResponse;
import org.activeledger.java.sdk.key.management.Encryption;
import org.activeledger.java.sdk.signature.Sign;
import org.activeledger.java.sdk.storage.LocalStorage;
import org.activeledger.java.sdk.utility.Parsing;
import org.activeledger.java.sdk.utility.Utility;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component("OnboardIdentity")
public class OnboardIdentity {

	@Autowired
	private OnboardIdentityReq onboardIdentityReq;
	@Autowired
	private Environment env;

	ObjectMapper mapper;
	Parsing parsing;
	public OnboardIdentity() {
		mapper = new ObjectMapper();
		parsing=new Parsing();
	}

/*
 * Method for onboarding a new identity.
 * input: i: Keypair of the new identity
 * 		  ii: Encryption used  
 * 		  iii: Preferred key name.
 * output: JSONObject containing identity. Utility function parsejson can be used to extract identity 
 */
	public TxResponse onboard(KeyPair keyPair, Encryption encrp, String keyName) throws Exception {/*
		System.out.println("-----private in onboard-----"+Utility.convertToStringPemFormat(keyPair.getPrivate()));
		System.out.println("-----public in onboard-----"+Utility.convertToStringPemFormat(keyPair.getPublic()));*/
	
		OnboardTransaction onboardTransaction = new OnboardTransaction();
		OnboardTxObject txObject = new OnboardTxObject();
		Identity identity = new Identity();
		if (encrp == Encryption.RSA) {
			identity.setType(encrp.toString().toLowerCase());
		} else {
			identity.setType(env.getProperty("ec.curve"));// get the type of curve. Currently only secp256 is supported
		}
		identity.setPublicKey(Utility.convertToStringPemFormat(keyPair.getPublic()));//Convert public key object into poem formated string to store in the transaction
		Map<String, Identity> inputIdentity = new HashMap<>();
		Map<String, String> signature = new HashMap<>();
		inputIdentity.put(keyName, identity);
		txObject.setIdentityList(inputIdentity);
		txObject.setContract(env.getProperty("onboard.contract"));// for onboarding contract is onboard(fixed)
		txObject.setNamespace(env.getProperty("onboard.namespace"));// for onboarding namespace is default(fixed)
		AbstractApplicationContext ctx = ActiveledgerJavaSdkApplication.getContext();//Get application context
		Sign sign = (Sign) ctx.getBean("Sign");

		String txObjectJson = mapper.writeValueAsString(txObject);
		String signed = sign.signMessage(txObjectJson.getBytes(), keyPair, encrp);
		signature.put(keyName, signed);
		onboardTransaction.setSignature(signature);
		onboardTransaction.setTxObject(txObject);
		onboardTransaction.setSelfSign(true);

		//Transaction object is complete and is ready to send to Activeledger
		JSONObject jsonObj = new JSONObject(onboardIdentityReq.onBoardIdentity(onboardTransaction));
		Map<String,String> idMap=new HashMap<>();
		TxResponse txResp=new TxResponse();
		idMap=parsing.parseJson(jsonObj);
		if (idMap.get("id") != null) {
			txResp.setId(idMap.get("id"));
			String stream = idMap.get("id");
			LocalStorage.getStore().put("stream", stream);
			LocalStorage.getStore().put("keyName", keyName);
			
		} else {
			txResp.setError(idMap.get("error"));
			
		}
	
		System.out.println("-----streamid-----"+txResp.getId());
		return txResp;

	}

}
