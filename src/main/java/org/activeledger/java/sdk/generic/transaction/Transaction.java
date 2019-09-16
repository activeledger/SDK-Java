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
package org.activeledger.java.sdk.generic.transaction;

import java.security.KeyPair;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.DefaultValue;

import org.activeledger.java.sdk.activeledgerjavasdk.ActiveledgerJavaSdkApplication;
import org.activeledger.java.sdk.key.management.Encryption;
import org.activeledger.java.sdk.signature.Sign;
import org.activeledger.java.sdk.storage.LocalStorage;
import org.activeledger.java.sdk.utility.Parsing;
import org.activeledger.java.sdk.utility.Utility;
import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
/*
 * 
 * This class represents the Transaction request object which will be sent to Activeledger
 * 
 * */
@JsonInclude(Include.NON_NULL)
public class Transaction {
	
	@JsonProperty("$territoriality")
	private String territoriality;
	@JsonProperty("$tx")
	private TxObject txObject;
	@DefaultValue("false")
	@JsonProperty("$selfsign")
	private boolean selfSign;
	@JsonProperty("$sigs")
	private Map<String, Object> signature;
	
	ObjectMapper mapper;

	public Transaction()
	{
		mapper=new ObjectMapper();
	}
	


	public String getTerritoriality() {
		return territoriality;
	}

	public void setTerritoriality(String territoriality) {
		this.territoriality = territoriality;
	}

	public TxObject getTxObject() {
		return txObject;
	}

	public void setTxObject(TxObject txObject) {
		this.txObject = txObject;
	}

	public boolean isSelfSign() {
		return selfSign;
	}

	public void setSelfSign(boolean selfSign) {
		this.selfSign = selfSign;
	}

	public Map<String, Object> getSignature() {
		return signature;
	}

	public void setSignature(Map<String, Object> signature) {
		this.signature = signature;
	}

	public void createTransaction(TxObject txObject,String territoriaity,boolean selfSign) throws Exception
	{
		
		Map<String,Object> temp=new HashMap<>();
		
		//Get values from localstorage
		String keyName=(String)LocalStorage.getStore().get("keyName");
		KeyPair keyPair=(KeyPair)LocalStorage.getStore().get("keyPair");
		String stream=(String)LocalStorage.getStore().get("stream");
		Encryption type=(Encryption)LocalStorage.getStore().get("type");
		for(Map.Entry<String,Object> inputMap: txObject.getInputIdentity().entrySet())
		{
			Map<String,Object> value=(Map)inputMap.getValue();
			value.put("$stream", stream);
			txObject.getInputIdentity().clear();
			temp.put(keyName, value);
			
		}
		txObject.setInputIdentity(temp);
		
		Sign sign = (Sign) ActiveledgerJavaSdkApplication.getContext().getBean("Sign");
		
		String signed = sign.signMessage(mapper.writeValueAsBytes(txObject), keyPair, type);
		Map<String, Object> signature = new HashMap<>();
		
		signature.put(stream, signed);
		this.setSignature(signature);
		this.setTxObject(txObject);
		this.setSelfSign(selfSign);
		this.setTerritoriality(territoriaity);
		
	}
	
	
	public TxResponse createAndSendTransaction(TxObject txObject,String territoriaity,boolean selfSign) throws Exception
	{
		//Transaction transaction=new Transaction();
		Map<String,Object> temp=new HashMap<>();
		
		//Get values from localstorage
		String keyName=(String)LocalStorage.getStore().get("keyName");
		KeyPair keyPair=(KeyPair)LocalStorage.getStore().get("keyPair");
		String stream=(String)LocalStorage.getStore().get("stream");
		Encryption type=(Encryption)LocalStorage.getStore().get("type");
		
		for(Map.Entry<String,Object> inputMap: txObject.getInputIdentity().entrySet())
		{
			Map<String,Object> value=(Map)inputMap.getValue();
			value.put("$stream", stream);
			txObject.getInputIdentity().remove(inputMap.getKey());
			temp.put(keyName, value);
			
		}
		txObject.setInputIdentity(temp);
		Sign sign = (Sign) ActiveledgerJavaSdkApplication.getContext().getBean("Sign");
		String signed = sign.signMessage(mapper.writeValueAsBytes(txObject), keyPair, type);
		signature.put(stream, signed);
		this.setSignature(signature);
		this.setTxObject(txObject);
		this.setSelfSign(selfSign);
		this.setTerritoriality(territoriaity);
		return this.sendTransaction();
		
	}
	public TxResponse sendTransaction() throws Exception
	{
		GenericTransaction genericTransaction = (GenericTransaction) ActiveledgerJavaSdkApplication.getContext().getBean("GenericTransaction");
		JSONObject genericTransactionOutput = genericTransaction.transaction(this);
		Map<String,String> idMap=new HashMap<>();
		TxResponse txResp=new TxResponse();
		Parsing parsing=new Parsing();
		idMap=parsing.parseJson(genericTransactionOutput);
		if (idMap.get("id") != null) {
			txResp.setId(idMap.get("id"));	
		} else {
			txResp.setError(idMap.get("error"));	
		}
		
		return txResp;
		
	}
}
