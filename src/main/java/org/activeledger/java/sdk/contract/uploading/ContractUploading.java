package org.activeledger.java.sdk.contract.uploading;

import java.util.HashMap;
import java.util.Map;

import org.activeledger.java.sdk.nhpk.NHPK;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component("ContractUploading")
public class ContractUploading {
	
	final static Logger logger = Logger.getLogger(ContractUploading.class);
	
	@Autowired
	ContractUploadingReq contractUploadingReq;
	@Autowired
	NHPK nhpk1;
	ObjectMapper mapper;

	public ContractUploading() {
		mapper = new ObjectMapper();
	}

	public JSONObject uploadContract(ContractUploadingTransaction contractUploadingTransaction) throws Exception
	{

			/*ContractUploadingTransaction contractUploadingTransaction=new ContractUploadingTransaction();
			ContractUploadingTxObject txObject=new ContractUploadingTxObject();
			ContractUploadingIdentityList contractUploadingIdentityList=new ContractUploadingIdentityList();
			Map<String,ContractUploadingIdentityList> identityMap=new HashMap<>();
			
			txObject.setContract(contractUploadModel.getContract());
			txObject.setNamespace(contractUploadModel.getNamespace());
			contractUploadingIdentityList.setContract(contractUploadModel.getSmartContract());
			contractUploadingIdentityList.setName(contractUploadModel.getName());
			contractUploadingIdentityList.setNamespace(contractUploadModel.getKeyNamespace());
			contractUploadingIdentityList.setVersion(contractUploadModel.getVersion());
			identityMap.put(contractUploadModel.getIdentity(), contractUploadingIdentityList);
			txObject.setIdentityList(identityMap);
			contractUploadingTransaction.setTxObject(txObject);
			contractUploadingTransaction.setSelfSign(contractUploadModel.isSelfSign());
			contractUploadingTransaction.setSignature(contractUploadModel.getSignature());*/
	
			logger.debug(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(contractUploadingTransaction));;
			 JSONObject jsonObj = new JSONObject(contractUploadingReq.uploadContract(contractUploadingTransaction));
			 logger.debug(jsonObj);
			 return jsonObj;
			
	}
	
/*	public static void main(String[] args) {
	ContractUploadModel contractUploadModel=new ContractUploadModel();
		contractUploadModel.setContract("contract");
		contractUploadModel.setNamespace("default");
		contractUploadModel.setEncrp(Encryption.EC);
		contractUploadModel.setIdentity("23b9f2d6f41061508bef63a7173168bcf2cb1c2e34723c28e37d32deeac1e5b1");
		contractUploadModel.setKeyNamespace("default");
		
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
		
	}*/
	
}

