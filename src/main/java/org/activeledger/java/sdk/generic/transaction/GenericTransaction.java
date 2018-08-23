package org.activeledger.java.sdk.generic.transaction;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.HashMap;
import java.util.Map;

import org.activeledger.java.sdk.connection.Connection;
import org.activeledger.java.sdk.key.management.Encryption;
import org.activeledger.java.sdk.key.management.KeyGen;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
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

		/*	Transaction transaction=new Transaction();
			TxObject txObject=new TxObject();

	
			txObject.setInputIdentity(transactionModel.getTxObject().getInputIdentity());
			txObject.setOutputIdentity(transactionModel.getTxObject().getOutputIdentity());
			txObject.setStreamState(transactionModel.getTxObject().getStreamState());
			txObject.setContract(transactionModel.getTxObject().getContract());
			txObject.setNamespace(transactionModel.getTxObject().getNamespace());
			transaction.setTxObject(txObject);
		
			transaction.setSignature(transactionModel.getSignature());
			transaction.setSelfSign(transactionModel.isSelfSign());
			*/
			logger.debug(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(transaction));
			JSONObject jsonObj = new JSONObject(transactionReq.transaction(transaction));
			logger.debug(jsonObj.toString());
			return jsonObj;
	}
	
/*	public static void main(String[] args) throws JsonProcessingException {
	
		Transaction tm=new Transaction();
		TxObject txObject=new TxObject();
		tm.setSelfSign(true);
		txObject.setContract("setup");
		txObject.setNamespace("namespace");
	
	
		
		
		Map<String,Object> inputIdentity=new HashMap<>();
		Map<String,Object> inputIdentityMap=new HashMap<>();
		Map<String,Object> nestedIdentityMap=new HashMap<>();
		Map<String,Object> nestedIdentityMap1=new HashMap<>();
		nestedIdentityMap1.put("publicKey","123");
		nestedIdentityMap1.put("type",Encryption.RSA.toString());
		nestedIdentityMap.put("Identity", nestedIdentityMap1);
	
		inputIdentityMap.put("identity",nestedIdentityMap1);
		inputIdentityMap.put("host","127.0.0.1");
		inputIdentityMap.put("port","5260");
		inputIdentity.put("node", inputIdentityMap);
		
		txObject.setInputIdentity(inputIdentity);
		tm.setTxObject(txObject);
		
		ObjectMapper newmap=new ObjectMapper();
		System.out.println("tx Object"+newmap.writerWithDefaultPrettyPrinter().writeValueAsString(tm));;
		
		//http://testnet-uk.activeledger.io:5260
		
		
		

		//GenericTransaction gt=new GenericTransaction();

		
	//	gt.transaction(tm,conn);

	}
	*/
	

}
