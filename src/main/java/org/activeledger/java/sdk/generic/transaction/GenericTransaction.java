package org.activeledger.java.sdk.generic.transaction;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.HashMap;
import java.util.Map;

import org.activeledger.java.sdk.contract.uploading.ContractUploadingReq;
import org.activeledger.java.sdk.key.management.Encryption;
import org.activeledger.java.sdk.key.management.KeyGen;
import org.activeledger.java.sdk.nhpk.NHPK;
import org.activeledger.java.sdk.nhpk.NHPKModel;
import org.activeledger.java.sdk.onboard.OnboardIdentityReq;
import org.activeledger.java.sdk.utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;

public class GenericTransaction {
	
	
	

	@Autowired
	ContractUploadingReq contractUploadingReq;

	/*@Autowired
	OnboardIdentityReq onBoardingIdentityReq;
*/	
	
	ObjectMapper mapper;

	public GenericTransaction() {
		mapper = new ObjectMapper();
	
		
	}

	public void transaction(TransactionModel transactionModel )
	{
		
		try {
			Transaction transaction=new Transaction();
			TxObject txObject=new TxObject();

			Map<String,Object> signatureMap=new HashMap<>();
			txObject.setInputIdentity(transactionModel.getTxObject().getInputIdentity());
			txObject.setOutputIdentity(transactionModel.getTxObject().getOutputIdentity());
			txObject.setStreamState(transactionModel.getTxObject().getStreamState());
			txObject.setContract(transactionModel.getTxObject().getContract());
			txObject.setNamespace(transactionModel.getTxObject().getNamespace());
			transaction.setTxObject(txObject);
		
			String signTransactionObject = mapper.writeValueAsString(transaction.getTxObject());
			String signature=OnboardIdentityReq.signMessage(signTransactionObject.getBytes(), transactionModel.getKeyPair(), transactionModel.getEncrp());
			Map.Entry<String,Object> entry = transactionModel.getTxObject().getInputIdentity().entrySet().iterator().next();
			signatureMap.put(entry.getKey(),signature);
			transaction.setSignature(signatureMap);
			transaction.setSelfSign(true);
				
			System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(transaction));;
			
			}
			catch(Exception e)
			{
				throw new IllegalArgumentException("Unable to sign object:"+e.getMessage());
			}
	}
	
	public static void main(String[] args) {
	
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
		Map<String,Object> outputIdentity=new HashMap<>();
		Map<String,String> outputIdentityMap=new HashMap<>();
		
		Map<String,Object> streamState=new HashMap<>();
		Map<String,String> streamStateMap=new HashMap<>();
		txObjectModel.setInputIdentity(inputIdentity);
		tm.setTxObject(txObjectModel);
		
		
		
		
		

		GenericTransaction gt=new GenericTransaction();

		
		gt.transaction(tm);

	}
	
	

}
