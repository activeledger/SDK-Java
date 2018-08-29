package org.activeledger.java.sdk.onboard;

import java.security.KeyPair;
import java.util.HashMap;
import java.util.Map;

import org.activeledger.java.sdk.activeledgerjavasdk.ActiveledgerJavaSdkApplication;
import org.activeledger.java.sdk.key.management.Encryption;
import org.activeledger.java.sdk.signature.Sign;
import org.activeledger.java.sdk.utility.PemFile;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;


@Component("OnboardIdentity")
public class OnboardIdentity {
	
	final static Logger logger = Logger.getLogger(OnboardIdentity.class);

	@Autowired
	private OnboardIdentityReq onboardIdentityReq;
	@Autowired
	private Environment env;
	
	ObjectMapper mapper;


	public OnboardIdentity() {
		mapper = new ObjectMapper();
	}

	

	public JSONObject onboard(KeyPair keyPair,Encryption encrp,String keyName) throws Exception
	{
		
		
		OnboardTransaction onboardTransaction=new OnboardTransaction();
		OnboardTxObject txObject=new OnboardTxObject();
		Identity identity=new Identity();
		if(encrp==Encryption.RSA)
		identity.setType(encrp.toString().toLowerCase());
		else
		identity.setType(env.getProperty("ec.curve"));
			
		identity.setPublicKey(PemFile.convertToStringPemFormat(keyPair.getPublic()));
		Map<String ,Identity> inputIdentity=new HashMap<>();
		Map<String ,String> signature=new HashMap<>();
		inputIdentity.put(keyName, identity);
		txObject.setIdentityList(inputIdentity);
		txObject.setContract(env.getProperty("onboard.contract"));
		txObject.setNamespace(env.getProperty("onboard.namespace"));
		AbstractApplicationContext ctx=ActiveledgerJavaSdkApplication.getContext();
		Sign sign=(Sign)ctx.getBean("Sign");
		
		String txObjectJson=mapper.writeValueAsString(txObject);
		String signed=sign.signMessage(txObjectJson.getBytes(), keyPair, encrp);
		signature.put(keyName, signed);
		onboardTransaction.setSignature(signature);
		onboardTransaction.setTxObject(txObject);
		onboardTransaction.setSelfSign(true);
		
		JSONObject jsonObj = new JSONObject(onboardIdentityReq.onBoardIdentity(onboardTransaction));
		
		return jsonObj;
		
	}
	
}
