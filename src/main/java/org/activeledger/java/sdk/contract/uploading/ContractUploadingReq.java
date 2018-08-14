package org.activeledger.java.sdk.contract.uploading;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ContractUploadingReq {


	
	ObjectMapper mapper;

	public ContractUploadingReq() {
		mapper = new ObjectMapper();
	}
	
	public void uploadContract(ContractUploadingTransaction contractUploadingTransaction)
	{
		 //System.out.println("JSON:"+transactionJson);;
		try {
        	System.out.println("Sent transaction:"+mapper.writerWithDefaultPrettyPrinter().writeValueAsString(contractUploadingTransaction));
        	String contractUploadingJson = mapper.writeValueAsString(contractUploadingTransaction);
        	
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
