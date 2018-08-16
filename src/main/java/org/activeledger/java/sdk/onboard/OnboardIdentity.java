package org.activeledger.java.sdk.onboard;

import java.security.KeyPair;

import org.activeledger.java.sdk.key.management.Encryption;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;


@Component("OnboardIdentity")
public class OnboardIdentity {
	
	final static Logger logger = Logger.getLogger(OnboardIdentity.class);

	@Autowired
	OnboardIdentityReq onboardIdentityReq;
	
	ObjectMapper mapper;


	public OnboardIdentity() {
		mapper = new ObjectMapper();
	}

	

	public JSONObject onboard(OnboardModel onboardModel,KeyPair keyPair, Encryption encrp) throws Exception
	{

		OnboardTransaction transaction = new OnboardTransaction();
		OnboardIdentityList identityList = new OnboardIdentityList();
		Identity identity = new Identity();
		OnboardTxObject txObject = new OnboardTxObject();
		

		identity.setType(encrp.toString());

		// Reading PEM formatted public key in string format
		//String pubKey = Utility.readFileAsString("pub-key.pem");

		identity.setPublicKey(onboardModel.getPublicKey());
		identityList.setIdentity(identity);

		txObject.setIdentityList(identityList);
		txObject.setContract(onboardModel.getContract());
		txObject.setNamespace(onboardModel.getNamespace());

		transaction.setTxObject(txObject);
		transaction.setSelfSign(onboardModel.isSelfSign());
		transaction.setSignature(onboardModel.getSignature());

			
		logger.debug(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(transaction));
		JSONObject jsonObj = new JSONObject(onboardIdentityReq.onBoardIdentity(transaction));
		logger.debug((jsonObj));
		return jsonObj;
	}


	

}
