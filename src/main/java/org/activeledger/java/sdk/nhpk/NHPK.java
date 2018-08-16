package org.activeledger.java.sdk.nhpk;

import java.util.HashMap;
import java.util.Map;

import org.activeledger.java.sdk.signature.Sign;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
@Component("NHPK")
public class NHPK {
	
	final static Logger logger = Logger.getLogger(NHPK.class);
	
	@Autowired
	NHPKReq nhpkReq;

	@Autowired
	Sign sign;
	@Value("${transaction.default.namepsace}")
	private String test;
	
	ObjectMapper mapper;

	public NHPK() {
		mapper = new ObjectMapper();
	}

	public JSONObject nhpk(NHPKModel nhpkModel) throws Exception
	{
		
			NHPKTransaction nhpkTransaction=new NHPKTransaction();
			NHPKTxObject txObject=new NHPKTxObject();
			NHPKIdentityList nhpkIdentityList=new NHPKIdentityList();
			Map<String,NHPKIdentityList> identityMap=new HashMap<>();
			
			txObject.setContract(nhpkModel.getContract());
			txObject.setNamespace(nhpkModel.getNamespace());
			//Utility.writePem("publickey.pem", "PUBLIC KEY", nhpkModel.getKeyPair().getPublic());
			nhpkIdentityList.setNhpk(nhpkModel.getPublicKey());
			identityMap.put(nhpkModel.getIdentity(), nhpkIdentityList);
			txObject.setIdentityList(identityMap);
			nhpkTransaction.setTxObject(txObject);
			nhpkTransaction.setSelfSign(nhpkModel.isSelfSign());
			nhpkTransaction.setSignature(nhpkModel.getSignature());
			
			logger.debug(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(nhpkTransaction));;
			JSONObject jsonObj = new JSONObject( nhpkReq.nhpkTransaction(nhpkTransaction));
			logger.debug((jsonObj));
			return jsonObj;
		
	}
	
/*	public static void main(String[] args) {
	
		ActiveledgerJavaSdkApplication sdk=new ActiveledgerJavaSdkApplication();
		AbstractApplicationContext ctx=ActiveledgerJavaSdkApplication.init();
		NHPKModel nhpkModel=new NHPKModel();
		nhpkModel.setContract("onboard");
		nhpkModel.setNamespace("default");
		nhpkModel.setEncrp(Encryption.EC);
		nhpkModel.setIdentity("23b9f2d6f41061508bef63a7173168bcf2cb1c2e34723c28e37d32deeac1e5b1");
		
		
		KeyGen keygen=new KeyGen();
		try {
			nhpkModel.setKeyPair(keygen.generateKeyPair(Encryption.RSA));
		} catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidAlgorithmParameterException
				| IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	

		NHPK nhpk = (NHPK) ctx.getBean("NHPK");

		System.out.println("---:"+nhpk.getTest());;
		nhpk.nhpk(nhpkModel);

	}

	public String getTest() {
		return test;
	}

	public void setTest(String test) {
		this.test = test;
	}*/
	
	

}
