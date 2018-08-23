package org.activeledger.java.sdk.nhpk;

import org.activeledger.java.sdk.signature.Sign;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
@Component("NHPK")
public class NHPK {
	
	final static Logger logger = Logger.getLogger(NHPK.class);
	
	@Autowired
	NHPKReq nhpkReq;

	@Autowired
	Sign sign;
	
	ObjectMapper mapper;

	public NHPK() {
		mapper = new ObjectMapper();
	}

	public JSONObject nhpk(NHPKTransaction nhpkTransaction) throws Exception
	{

			logger.debug(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(nhpkTransaction));;
			JSONObject jsonObj = new JSONObject( nhpkReq.nhpkTransaction(nhpkTransaction));
			logger.debug((jsonObj));
			return jsonObj;
		
	}
	

}
