package org.activeledger.java.sdk.transfer.funds;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.HashMap;
import java.util.Map;

import org.activeledger.java.sdk.contract.uploading.ContractUploadModel;
import org.activeledger.java.sdk.contract.uploading.ContractUploading;
import org.activeledger.java.sdk.key.management.Encryption;
import org.activeledger.java.sdk.key.management.KeyGen;
import org.activeledger.java.sdk.onboard.OnboardIdentityReq;

import com.fasterxml.jackson.databind.ObjectMapper;

public class TransferFunds {
	
	
	ObjectMapper mapper;

	public TransferFunds() {
		// TODO Auto-generated constructor stub
		mapper = new ObjectMapper();
	}
	
	
	public void transferFunds(TransferFundsModel transferFundsModel)
	{
		try {
		TransferFundsTransaction transferFundsTransaction=new TransferFundsTransaction();
		TransferFundsTxObject transferFundsTxObject=new TransferFundsTxObject();
		Map<String,TransferFundsIdentity> inputIdentityMap=new HashMap<>();
		Map<String,TransferFundsIdentity> outputIdentityMap=new HashMap<>();
		Map<String,String> signature=new HashMap<>();
		TransferFundsIdentity transferFundsInputIdentity=new TransferFundsIdentity();
		TransferFundsIdentity transferFundsOutputIdentity=new TransferFundsIdentity();
		
		transferFundsTxObject.setContract(transferFundsModel.getContract());
		transferFundsTxObject.setEntry(transferFundsModel.getEntry());
		transferFundsTxObject.setNamespace(transferFundsModel.getNamespace());
		
		
		transferFundsInputIdentity.setSymbol(transferFundsModel.getInputIdentitySymbol());
		transferFundsInputIdentity.setAmount(transferFundsModel.getAmount());
		
		inputIdentityMap.put(transferFundsModel.getInputIdentity(),transferFundsInputIdentity );
		
		transferFundsOutputIdentity.setAmount(transferFundsModel.getAmount());
		outputIdentityMap.put(transferFundsModel.getOutputIdentity(), transferFundsOutputIdentity);
		transferFundsTxObject.setOutputIdentityList(outputIdentityMap);
		transferFundsTxObject.setInputIdentityList(inputIdentityMap);
		
		transferFundsTransaction.setTxObject(transferFundsTxObject);
		
		
		String signTransactionObject = mapper.writeValueAsString(transferFundsTransaction.getTxObject());
		String sign=OnboardIdentityReq.signMessage(signTransactionObject.getBytes(), transferFundsModel.getKeyPair(), transferFundsModel.getEncrp());
		
	
		signature.put(transferFundsModel.getInputIdentity(), sign);
		transferFundsTransaction.setSignature(signature);
		
		
		 System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(transferFundsTransaction));;

		}catch(Exception e)
		{
			throw new IllegalArgumentException("Unable to sign object:"+e.getMessage());
		}
		
	}
	
	
	public static void main(String[] args) {
		
		TransferFundsModel transferUploadModel=new TransferFundsModel();
		transferUploadModel.setContract("fund");
		transferUploadModel.setNamespace("default");
		transferUploadModel.setEncrp(Encryption.RSA);
		transferUploadModel.setInputIdentity("123456");
		transferUploadModel.setOutputIdentity("789");
		transferUploadModel.setAmount(5.00);
		transferUploadModel.setEntry("transfer");
		transferUploadModel.setInputIdentitySymbol("B$");
		
		
		KeyGen keygen=new KeyGen();
		try {
			transferUploadModel.setKeyPair(keygen.generateKeyPair(Encryption.RSA));
		} catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidAlgorithmParameterException
				| IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		TransferFunds tf=new TransferFunds();

		
		tf.transferFunds(transferUploadModel);

	}
	
		
}
