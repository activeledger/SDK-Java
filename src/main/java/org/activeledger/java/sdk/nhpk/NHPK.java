package org.activeledger.java.sdk.nhpk;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.HashMap;
import java.util.Map;

import org.activeledger.java.sdk.contract.uploading.ContractUploadModel;
import org.activeledger.java.sdk.contract.uploading.ContractUploading;
import org.activeledger.java.sdk.contract.uploading.ContractUploadingIdentityList;
import org.activeledger.java.sdk.contract.uploading.ContractUploadingReq;
import org.activeledger.java.sdk.contract.uploading.ContractUploadingTransaction;
import org.activeledger.java.sdk.contract.uploading.ContractUploadingTxObject;
import org.activeledger.java.sdk.key.management.Encryption;
import org.activeledger.java.sdk.key.management.KeyGen;
import org.activeledger.java.sdk.onboard.OnboardIdentityReq;
import org.activeledger.java.sdk.utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;

public class NHPK {
	
	
	@Autowired
	ContractUploadingReq contractUploadingReq;

	/*@Autowired
	OnboardIdentityReq onBoardingIdentityReq;
*/	
	
	ObjectMapper mapper;

	public NHPK() {
		mapper = new ObjectMapper();
	
		
	}

	public void nhpk(NHPKModel nhpkModel )
	{//, KeyPair keyPair,Encryption encrp,String identity
		//sign the transaction
		try {
			NHPKTransaction nhpkTransaction=new NHPKTransaction();
			NHPKTxObject txObject=new NHPKTxObject();
			NHPKIdentityList nhpkIdentityList=new NHPKIdentityList();
			Map<String,NHPKIdentityList> identityMap=new HashMap<>();
			Map<String,String> signatureMap=new HashMap<>();
			
			
			txObject.setContract(nhpkModel.getContract());
			txObject.setNamespace(nhpkModel.getNamespace());
			Utility.writePem("publickey.pem", "PUBLIC KEY", nhpkModel.getKeyPair().getPublic());
			nhpkIdentityList.setNhpk(Utility.readFileAsString("publickey.pem"));
			identityMap.put(nhpkModel.getIdentity(), nhpkIdentityList);
			txObject.setIdentityList(identityMap);
			nhpkTransaction.setTxObject(txObject);
			
			// Sign message and set the signature in the transaction request
			String signTransactionObject = mapper.writeValueAsString(nhpkTransaction.getTxObject());
			String signature=OnboardIdentityReq.signMessage(signTransactionObject.getBytes(), nhpkModel.getKeyPair(), nhpkModel.getEncrp());
			
			
			signatureMap.put(nhpkModel.getIdentity(),signature);
			nhpkTransaction.setSignature(signatureMap);
			
			
			
			 System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(nhpkTransaction));;
			
			//String signature=OnboardIdentityReq.signMessage(signTransactionObject.getBytes());//, keyPair, encrp);
			 //contractUploadingReq.uploadContract(contractUploadingTransaction);
			
			}
			catch(Exception e)
			{
				throw new IllegalArgumentException("Unable to sign object:"+e.getMessage());
			}
	}
	
	public static void main(String[] args) {
	
		NHPKModel nhpkModel=new NHPKModel();
		nhpkModel.setContract("onboard");
		nhpkModel.setNamespace("default");
		nhpkModel.setEncrp(Encryption.RSA);
		nhpkModel.setIdentity("123456");
		
		
		KeyGen keygen=new KeyGen();
		try {
			nhpkModel.setKeyPair(keygen.generateKeyPair(Encryption.RSA));
		} catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidAlgorithmParameterException
				| IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	

		NHPK nhpk=new NHPK();

		
		nhpk.nhpk(nhpkModel);

	}
	
	

}
