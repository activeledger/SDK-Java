package org.activeledger.java.sdk.generic.transaction;

import org.activeledger.java.sdk.signature.Sign;
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

	@Autowired
	Sign sign;
	
	
	ObjectMapper mapper;

	public GenericTransaction() {
		mapper = new ObjectMapper();
	
		
	}

	public JSONObject transaction(TransactionModel transactionModel) throws Exception
	{

			Transaction transaction=new Transaction();
			TxObject txObject=new TxObject();

	
			txObject.setInputIdentity(transactionModel.getTxObject().getInputIdentity());
			txObject.setOutputIdentity(transactionModel.getTxObject().getOutputIdentity());
			txObject.setStreamState(transactionModel.getTxObject().getStreamState());
			txObject.setContract(transactionModel.getTxObject().getContract());
			txObject.setNamespace(transactionModel.getTxObject().getNamespace());
			transaction.setTxObject(txObject);
		
			transaction.setSignature(transactionModel.getSignature());
			transaction.setSelfSign(transactionModel.isSelfSign());
			
			logger.debug(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(transaction));
			JSONObject jsonObj = new JSONObject(transactionReq.transaction(transaction));
			logger.debug(jsonObj);
			return jsonObj;
	}
	
	/*public static void main(String[] args) {
	
		TransactionModel tm=new TransactionModel();
		tm.setEncrp(Encryption.RSA);
		tm.setSelfSign(true);
		TxObjectModel txObjectModel=new TxObjectModel();
		txObjectModel.setContract("setup");
		txObjectModel.setNamespace("namespace");
		
		KeyGen keygen=new KeyGen();
		try {
			tm.setKeyPair(keygen.generateKeyPair(Encryption.RSA));
		} catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidAlgorithmParameterException
				| IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		
		Map<String,Object> inputIdentity=new HashMap<>();
		Map<String,Object> inputIdentityMap=new HashMap<>();
		Map<String,Object> nestedIdentityMap=new HashMap<>();
		Map<String,Object> nestedIdentityMap1=new HashMap<>();
		nestedIdentityMap1.put("publicKey",Utility.readFileAsString("pub-key.pem"));
		nestedIdentityMap1.put("type",Encryption.RSA.toString());
		nestedIdentityMap.put("Identity", nestedIdentityMap1);
	
		inputIdentityMap.put("identity",nestedIdentityMap1);
		inputIdentityMap.put("host","127.0.0.1");
		inputIdentityMap.put("port","5260");
		inputIdentity.put("node", inputIdentityMap);

		txObjectModel.setInputIdentity(inputIdentity);
		tm.setTxObject(txObjectModel);
		
		//http://testnet-uk.activeledger.io:5260
		
		Connection conn=new Connection();
		conn.setProtocol("http");
		conn.setUrl("testnet-uk.activeledger.io");
		conn.setPort("5260");
		

		GenericTransaction gt=new GenericTransaction();

		
		gt.transaction(tm,conn);

	}
	*/
	

}
