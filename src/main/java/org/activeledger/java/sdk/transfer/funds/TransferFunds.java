package org.activeledger.java.sdk.transfer.funds;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component("TransferFunds")
public class TransferFunds {
	
	final static Logger logger = Logger.getLogger(TransferFunds.class);
	
	@Autowired
	TransferFundsReq transferFundsReq;
	
	ObjectMapper mapper;

	public TransferFunds() {
		mapper = new ObjectMapper();
		
	}
	
	
	public JSONObject transferFunds(TransferFundsTransaction transferFundsTransaction) throws Exception
	{
	
			
		logger.debug(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(transferFundsTransaction));
		JSONObject jsonObj = new JSONObject(transferFundsReq.transferFunds(transferFundsTransaction));
		logger.debug((jsonObj.toString(2)));
		return jsonObj;

		
	}
		
}
