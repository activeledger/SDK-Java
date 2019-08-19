package org.activeledger.java.sdk.activeledgerjavasdk;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.sse.InboundSseEvent;
import javax.ws.rs.sse.SseEventSource;

import org.activeledger.java.sdk.generic.transaction.Transaction;
import org.activeledger.java.sdk.generic.transaction.TxObject;
import org.activeledger.java.sdk.generic.transaction.TxResponse;
import org.activeledger.java.sdk.key.management.Encryption;
import org.activeledger.java.sdk.key.management.KeyGen;
import org.activeledger.java.sdk.onboard.OnboardIdentity;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ActiveledgerJavaSdkApplicationTest {

	private AbstractApplicationContext ctx;
	private ObjectMapper mapper;
	private Map<String, String> map;

	public ActiveledgerJavaSdkApplicationTest() {
		mapper = new ObjectMapper();
		map = new HashMap<>();
	}

	@Before
	public void contextLoads() {
		ctx = ActiveledgerJavaSdkApplication.getContext();
		ActiveledgerJavaSdkApplication.setConnection("http", "testnet-uk.activeledger.io", "5260");
	}

	// Generate RSA keyPair
	public KeyPair generateRSAkeyPair()
			throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException, IOException {
		KeyGen keyGen = (KeyGen) ctx.getBean("KeyGen");
		KeyPair keyPair = keyGen.generateKeyPair(Encryption.RSA);
		return keyPair;
	}

	// Generate ECDSA KeyPair
	public KeyPair generateECDSAkeyPair()
			throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException, IOException {
		KeyGen keyGen = (KeyGen) ctx.getBean("KeyGen");
		KeyPair keyPair = keyGen.generateKeyPair(Encryption.EC);
		return keyPair;
	}
	

	@Test
	public void TempTest() throws Exception {
		KeyPair keyPair = generateRSAkeyPair();
		Transaction transaction = new Transaction();
		TxObject txObject = new TxObject();
		Map<String, Object> inputIdentityMap = new HashMap<>();
		Map<String, Object> signature = new HashMap<>();

		// onboarding first identity
		OnboardIdentity onboardIdentity = (OnboardIdentity) ctx.getBean("OnboardIdentity");
		TxResponse resp = onboardIdentity.onboard(keyPair, Encryption.RSA, "writer");
		//String inputIdentity = parseJson(inJson).get("id");// can be later used
		//System.out.println("stream id:"+resp.getId());
		
		
		txObject.setContract("easa"); // sample contract
																									// stream id
		txObject.setNamespace("pdfnamespace"); // namespace of the contract
		
		JSONObject writer=new JSONObject();
	//	writer.put("$stream",inputIdentity);
		
		JSONObject organization=new JSONObject();
		organization.put("nameAndAddress", "Test Name ltd and Address");
		organization.put("workOrder","456");
		writer.put("organization",organization);
		writer.put("formTrackingNumber","123");
		inputIdentityMap.put("writer", writer.toMap());
		
		txObject.setInputIdentity(inputIdentityMap);
		
		transaction=transaction.createTransaction(txObject,null, false);
		System.out.println(mapper.writeValueAsString(transaction));
		
	}
		
		
	


	

}
