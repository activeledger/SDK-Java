package org.activeledger.java.sdk.onboard;

import org.activeledger.java.sdk.key.management.Encryption;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;


@Component("OnboardIdentity")
public class OnboardIdentity {
	
	final static Logger logger = Logger.getLogger(OnboardIdentity.class);

	@Autowired
	OnboardIdentityReq onboardIdentityReq;
	
	ObjectMapper mapper;


	public OnboardIdentity() {
		mapper = new ObjectMapper();
	}

	

	public JSONObject onboard(OnboardTransaction onboardTransaction,Encryption encrp) throws Exception
	{
		logger.debug(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(onboardTransaction));
		JSONObject jsonObj = new JSONObject(onboardIdentityReq.onBoardIdentity(onboardTransaction));
		logger.debug((jsonObj));
		return jsonObj;
	}
	
	

}
