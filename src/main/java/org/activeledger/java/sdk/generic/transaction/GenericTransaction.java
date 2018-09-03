package org.activeledger.java.sdk.generic.transaction;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component("GenericTransaction")
public class GenericTransaction {

	@Autowired
	TransactionReq transactionReq;
	ObjectMapper mapper;

	public GenericTransaction() {
		mapper = new ObjectMapper();
	}

	public JSONObject transaction(Transaction transaction) throws Exception {

		JSONObject jsonObj = new JSONObject(transactionReq.transaction(transaction));

		return jsonObj;
	}

}
