package org.activeledger.java.sdk.nhpk;

import org.activeledger.java.sdk.contract.uploading.ContractUploadingTransaction;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class NHPKReq {

	
	ObjectMapper mapper;

	public NHPKReq() {
		mapper = new ObjectMapper();
	}
	
	public void uploadContract(NHPKTransaction nhpkTransaction)
	{
		 //System.out.println("JSON:"+transactionJson);;
		try {
        	System.out.println("Sent transaction:"+mapper.writerWithDefaultPrettyPrinter().writeValueAsString(nhpkTransaction));
        	String contractUploadingJson = mapper.writeValueAsString(nhpkTransaction);
        	
        	HttpClient httpclient = HttpClients.createDefault();
        	HttpPost httppost = new HttpPost("http://testnet-eu.activeledger.io:5260");
        	//HttpPost httppost = new HttpPost("http://127.0.0.1:5260");
        	StringEntity entity=new StringEntity(contractUploadingJson);
        	entity.setContentType("application/json");
        	httppost.setEntity(entity);
        	HttpResponse response = httpclient.execute(httppost);
        	HttpEntity resp = response.getEntity();

        	if (resp != null) {
        		String responseAsString = EntityUtils.toString(response.getEntity());
        	    System.out.println(responseAsString);
        	}

        }
        catch(Exception e)
        {
        	throw new IllegalArgumentException("Exception occurred while onboaring:"+e.getMessage());
        }
	}

	
	
}
