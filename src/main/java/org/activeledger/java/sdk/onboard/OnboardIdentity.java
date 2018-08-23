package org.activeledger.java.sdk.onboard;

import java.security.KeyPair;
import java.util.HashMap;
import java.util.Map;

import org.activeledger.java.sdk.key.management.Encryption;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
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

	

	public JSONObject onboard(OnboardTransaction onboardTransaction,Encryption encrp) throws Exception
	{

		/*OnboardTransaction transaction = new OnboardTransaction();
		OnboardTxObject txObject = new OnboardTxObject();

		txObject.setIdentityList(onboardModel.getTxObject().getIdentity());
		txObject.setContract(onboardModel.getTxObject().getContract());
		txObject.setNamespace(onboardModel.getTxObject().getNamespace());

		transaction.setTxObject(txObject);
		transaction.setSelfSign(onboardModel.isSelfSign());
		
		transaction.setSignature(onboardModel.getSignature());
*/
			
		logger.debug(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(onboardTransaction));
		JSONObject jsonObj = new JSONObject(onboardIdentityReq.onBoardIdentity(onboardTransaction));
		logger.debug((jsonObj));
		return jsonObj;
	}
	
/*public static void main(String []args) throws JsonProcessingException
	{

		
	OnboardTransaction onboard=new OnboardTransaction();
		OnboardTxObject txObject=new OnboardTxObject();
		Identity identity=new Identity();
		identity.setPublicKey("123");
		identity.setType(Encryption.RSA);
		Map<String,Identity> map=new HashMap<>();
		map.put("key", identity);
		txObject.setContract("contract");
		txObject.setIdentityList(map);
		txObject.setNamespace("default");
		onboard.setSelfSign(true);
		ObjectMapper newmap=new ObjectMapper();
		System.out.println("tx Object"+newmap.writerWithDefaultPrettyPrinter().writeValueAsString(txObject));;
		
		//onboard.setSignature(signature);
	}
*/

	

}
