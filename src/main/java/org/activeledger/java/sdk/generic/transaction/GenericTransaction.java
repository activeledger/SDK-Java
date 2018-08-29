package org.activeledger.java.sdk.generic.transaction;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component("GenericTransaction")
public class GenericTransaction {
	
	
	final static Logger logger = Logger.getLogger(GenericTransaction.class);

	@Autowired
	TransactionReq transactionReq;
	ObjectMapper mapper;

	public GenericTransaction() {
		mapper = new ObjectMapper();
	}

	public JSONObject transaction(Transaction transaction) throws Exception
	{

		//	logger.debug(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(transaction));
			JSONObject jsonObj = new JSONObject(transactionReq.transaction(transaction));
		//	logger.debug(jsonObj.toString());
			return jsonObj;
	}
	

}
