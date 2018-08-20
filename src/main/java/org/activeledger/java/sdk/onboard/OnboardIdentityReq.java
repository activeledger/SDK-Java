package org.activeledger.java.sdk.onboard;

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
public class OnboardIdentityReq extends Connection{
	
	final static Logger logger = Logger.getLogger(OnboardIdentityReq.class);
	
	ObjectMapper mapper;

	public OnboardIdentityReq() {
		mapper = new ObjectMapper();
	}

	
	public String onBoardIdentity(OnboardTransaction transaction)
    { 
    	//OnboardTransaction	transaction =onboardIdentiy.onboard(keyPair,encrp);

		//Gson gson = new Gson();
		//String transactionJson = gson.toJson(transaction);
        //System.out.println("JSON:"+transactionJson);;
        try {
        	String transactionJson = mapper.writeValueAsString(transaction);
        	
        	HttpClient httpclient = HttpClients.createDefault();
        	HttpPost httppost = new HttpPost(getConnectionURL());

        	StringEntity entity=new StringEntity(transactionJson);
        	entity.setContentType("application/json");
        	httppost.setEntity(entity);
        	HttpResponse response = httpclient.execute(httppost);
        	
        	String responseAsString = EntityUtils.toString(response.getEntity());

        	return responseAsString;
        	

        }
        catch(Exception e)
        {
        	logger.error("Exception occurred while onboaring",e);
        	throw new IllegalArgumentException("Exception occurred while onboaring:"+e.getMessage());
        }
	
    }

}

