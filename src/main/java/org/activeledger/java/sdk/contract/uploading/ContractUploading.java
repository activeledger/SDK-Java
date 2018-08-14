package org.activeledger.java.sdk.contract.uploading;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.HashMap;
import java.util.Map;

import org.activeledger.java.sdk.key.management.Encryption;
import org.activeledger.java.sdk.key.management.KeyGen;
import org.activeledger.java.sdk.onboard.OnboardIdentityReq;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ContractUploading {
	
	@Autowired
	ContractUploadingReq contractUploadingReq;

	/*@Autowired
	OnboardIdentityReq onBoardingIdentityReq;
*/	
	
	ObjectMapper mapper;

	public ContractUploading() {
		mapper = new ObjectMapper();
	
		
	}

	public void uploadContract(ContractUploadModel contractUploadModel )
	{//, KeyPair keyPair,Encryption encrp,String identity
		//sign the transaction
		try {
			ContractUploadingTransaction contractUploadingTransaction=new ContractUploadingTransaction();
			ContractUploadingTxObject txObject=new ContractUploadingTxObject();
			ContractUploadingIdentityList contractUploadingIdentityList=new ContractUploadingIdentityList();
			Map<String,ContractUploadingIdentityList> identityMap=new HashMap<>();
			Map<String,String> signatureMap=new HashMap<>();
			
			
			txObject.setContract(contractUploadModel.getContract());
			txObject.setNamespace(contractUploadModel.getNamespace());
			contractUploadingIdentityList.setContract(contractUploadModel.getSmartContract());
			contractUploadingIdentityList.setName(contractUploadModel.getName());
			contractUploadingIdentityList.setNamespace(contractUploadModel.getKeyNamespace());
			contractUploadingIdentityList.setVersion(contractUploadModel.getVersion());
			identityMap.put(contractUploadModel.getIdentity(), contractUploadingIdentityList);
			txObject.setIdentityList(identityMap);
			contractUploadingTransaction.setTxObject(txObject);
			
			// Sign message and set the signature in the transaction request
			String signTransactionObject = mapper.writeValueAsString(contractUploadingTransaction.getTxObject());
			String signature=OnboardIdentityReq.signMessage(signTransactionObject.getBytes(), contractUploadModel.getKeyPair(), contractUploadModel.getEncrp());
			
			
			signatureMap.put(contractUploadModel.getIdentity(),signature);
			contractUploadingTransaction.setSignature(signatureMap);
			
			
			
			 System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(contractUploadingTransaction));;
			
			//String signature=OnboardIdentityReq.signMessage(signTransactionObject.getBytes());//, keyPair, encrp);
			 //contractUploadingReq.uploadContract(contractUploadingTransaction);
			
			}
			catch(Exception e)
			{
				throw new IllegalArgumentException("Unable to sign object:"+e.getMessage());
			}
	}
	
	public static void main(String[] args) {
	
		ContractUploadModel contractUploadModel=new ContractUploadModel();
		contractUploadModel.setContract("onboard");
		contractUploadModel.setNamespace("default");
		contractUploadModel.setEncrp(Encryption.RSA);
		contractUploadModel.setIdentity("123456");
		contractUploadModel.setKeyNamespace("ns1");
		
		KeyGen keygen=new KeyGen();
		try {
			contractUploadModel.setKeyPair(keygen.generateKeyPair(Encryption.RSA));
		} catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidAlgorithmParameterException
				| IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		contractUploadModel.setName("TestKey");
		contractUploadModel.setSmartContract("Base 46 encoded smart contract");
		contractUploadModel.setVersion("1");

		ContractUploading c=new ContractUploading();

		
		c.uploadContract(contractUploadModel);

	}
	
}

