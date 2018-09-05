package org.activeledger.java.sdk.activeledgerjavasdk;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.activeledger.java.sdk.generic.transaction.GenericTransaction;
import org.activeledger.java.sdk.generic.transaction.Transaction;
import org.activeledger.java.sdk.generic.transaction.TxObject;
import org.activeledger.java.sdk.key.management.Encryption;
import org.activeledger.java.sdk.key.management.KeyGen;
import org.activeledger.java.sdk.onboard.OnboardIdentity;
import org.activeledger.java.sdk.signature.Sign;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.minidev.json.parser.ParseException;

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

	// Transfer funds request
	@Test
	public void transferFundsTransactionTest() throws Exception {
		KeyPair firstKeyPair = generateRSAkeyPair();
		KeyPair secondKeyPair = generateECDSAkeyPair();
		Transaction transaction = new Transaction();
		TxObject txObject = new TxObject();
		Map<String, Object> inputIdentityMap = new HashMap<>();
		Map<String, Object> inputIdentityMap1 = new HashMap<>(); // instead of map, jsonObject can be used
		Map<String, Object> outputIdentityMap = new HashMap<>();
		Map<String, Object> outputIdentityMap1 = new HashMap<>(); // instead of map, jsonObject can be used
		Map<String, Object> signature = new HashMap<>();

		// onboarding first identity
		OnboardIdentity onboardIdentity = (OnboardIdentity) ctx.getBean("OnboardIdentity");
		JSONObject inJson = onboardIdentity.onboard(firstKeyPair, Encryption.RSA, "firstkey");
		String inputIdentity = parseJson(inJson).get("id");

		// onboarding second identity
		JSONObject outJson = onboardIdentity.onboard(secondKeyPair, Encryption.EC, "secondkey");
		String outputIdentity = parseJson(outJson).get("id");

		txObject.setContract("df9f84846ace992d7aa13b8f7d4295b4a0d54f178e0059d96208dd1b2183b297"); // sample contract
																									// stream id
		txObject.setNamespace("ns2"); // namespace of the contract
		inputIdentityMap1.put("symbol", "usd");
		inputIdentityMap1.put("amount", 5);
		inputIdentityMap.put(inputIdentity, inputIdentityMap1);

		outputIdentityMap1.put("amount", 5);
		outputIdentityMap.put(outputIdentity, outputIdentityMap1);
		txObject.setInputIdentity(inputIdentityMap);
		txObject.setOutputIdentity(outputIdentityMap);
		txObject.setEntry("transfer");
		transaction.setTxObject(txObject);
		Sign sign = (Sign) ctx.getBean("Sign");
		String signed = sign.signMessage(mapper.writeValueAsBytes(txObject), firstKeyPair, Encryption.RSA);
		signature.put(inputIdentity, signed);
		transaction.setSignature(signature);

		GenericTransaction genericTransaction = (GenericTransaction) ctx.getBean("GenericTransaction");
		JSONObject genericTransactionOutput = genericTransaction.transaction(transaction);
		Map<String, String> output = parseJson(genericTransactionOutput);
		assertEquals(output.get("total").toString(), output.get("commit").toString());
		// logger.info("Transaction output:"+genericTransactionOutput.toString(2));

	}

	@After
	public void destroy() {
		File file = new File("pub-key.pem");
		if (file.exists()) {
			file.delete();
		}
	}

	public void getArray(Object object2) throws ParseException {

		JSONArray jsonArr = (JSONArray) object2;

		for (int k = 0; k < jsonArr.length(); k++) {

			if (jsonArr.get(k) instanceof JSONObject) {
				parseJson((JSONObject) jsonArr.get(k));
			}

		}
	}

	public Map<String, String> parseJson(JSONObject jsonObject) throws ParseException {

		Set<String> set = jsonObject.keySet();
		Iterator<String> iterator = set.iterator();
		while (iterator.hasNext()) {
			Object obj = iterator.next();
			if (jsonObject.get(obj.toString()) instanceof JSONArray) {
				getArray(jsonObject.get(obj.toString()));
			} else {
				if (jsonObject.get(obj.toString()) instanceof JSONObject) {
					parseJson((JSONObject) jsonObject.get(obj.toString()));
				} else {

					map.put(obj.toString(), jsonObject.get(obj.toString()).toString());

				}
			}
		}
		return map;
	}

}
