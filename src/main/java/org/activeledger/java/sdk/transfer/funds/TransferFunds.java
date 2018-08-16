package org.activeledger.java.sdk.transfer.funds;

import java.util.HashMap;
import java.util.Map;

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
	
	
	public JSONObject transferFunds(TransferFundsModel transferFundsModel) throws Exception
	{
	
		TransferFundsTransaction transferFundsTransaction=new TransferFundsTransaction();
		TransferFundsTxObject transferFundsTxObject=new TransferFundsTxObject();
		Map<String,TransferFundsIdentity> inputIdentityMap=new HashMap<>();
		Map<String,TransferFundsIdentity> outputIdentityMap=new HashMap<>();
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
		transferFundsTransaction.setSelfSign(transferFundsModel.isSelfSign());
		
		transferFundsTransaction.setSignature(transferFundsModel.getSignature());

	
			
		logger.debug(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(transferFundsTransaction));
		JSONObject jsonObj = new JSONObject(transferFundsReq.transferFunds(transferFundsTransaction));
		logger.debug((jsonObj));
		return jsonObj;

		
	}
	
	
	/*public static void main(String[] args) throws Exception {
		
		TransferFundsModel transferUploadModel=new TransferFundsModel();
		transferUploadModel.setContract("fund");
		transferUploadModel.setNamespace("default");
		transferUploadModel.setEncrp(Encryption.EC);
		transferUploadModel.setInputIdentity("23b9f2d6f41061508bef63a7173168bcf2cb1c2e34723c28e37d32deeac1e5b1");
		transferUploadModel.setOutputIdentity("23b9f2d6f41061508bef63a7173168bcf2cb1c2e34723c28e37d32deeac1e5b1");
		transferUploadModel.setAmount(5.00);
		transferUploadModel.setEntry("transfer");
		transferUploadModel.setInputIdentitySymbol("B$");
		Map<String,String> signature=new HashMap<>();
		signature.put("23b9f2d6f41061508bef63a7173168bcf2cb1c2e34723c28e37d32deeac1e5b1", "123");
		
		transferUploadModel.setSignature(signature);
		
		AbstractApplicationContext ctx=new AnnotationConfigApplicationContext(AppConfig.class);
		
		Connection.setPort("5260");
		Connection.setProtocol("http");
		Connection.setUrl("127.0.0.1");	
		ActiveledgerJavaSdkApplication.init();
		
		TransferFunds tf = (TransferFunds) ctx.getBean("TransferFunds");
		//TransferFunds tf=new TransferFunds();

		tf.transferFunds(transferUploadModel);
		ctx.close();

	}*/
	
		
}
