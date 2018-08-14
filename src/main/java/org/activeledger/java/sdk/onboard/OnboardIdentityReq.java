package org.activeledger.java.sdk.onboard;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;

import javax.xml.bind.DatatypeConverter;

import org.activeledger.java.sdk.key.management.Encryption;
import org.activeledger.java.sdk.utility.PemFile;
import org.activeledger.java.sdk.utility.Utility;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class OnboardIdentityReq {

	ObjectMapper mapper;

	public OnboardIdentityReq() {
		mapper = new ObjectMapper();
	}

	public OnboardTransaction onboard(KeyPair keyPair, Encryption encrp)
	{

		OnboardTransaction transaction = new OnboardTransaction();
		OnboardIdentityList identityList = new OnboardIdentityList();
		Identity identity = new Identity();
		OnboardTxObject txObject = new OnboardTxObject();
		org.activeledger.java.sdk.onboard.Signature signature = new org.activeledger.java.sdk.onboard.Signature();

		if (encrp == Encryption.RSA)
			identity.setType("rsa");
		else if (encrp == Encryption.EC)
			identity.setType("secp256k1");

		// Reading PEM formatted public key in string format
		String pubKey = Utility.readFileAsString("pub-key.pem");

		identity.setPublicKey(pubKey);
		identityList.setIdentity(identity);

		txObject.setIdentityList(identityList);
		txObject.setContract("onboard");
		txObject.setNamespace("default");

		transaction.setTxObject(txObject);

		try {
		String signTransactionObject = mapper.writeValueAsString(txObject);
		// Sign message and set the signature in the transaction request
		signature.setIdentity(signMessage(signTransactionObject.getBytes(), keyPair, encrp));
		}
		catch(Exception e)
		{
			throw new IllegalArgumentException("Unable to sign object:"+e.getMessage());
		}

		transaction.setSelfSign(true);
		transaction.setSignature(signature);

		return transaction;
	}

	public static String signMessage(byte[] message, KeyPair keyPair, Encryption encrp) {
		Signature sign = null;
		KeyFactory factory = null;
	
		try {
			if (encrp == Encryption.RSA) {
				sign = Signature.getInstance("SHA256withRSA", "BC");
				factory = KeyFactory.getInstance("RSA", "BC");
			} else if (encrp == Encryption.EC) {
				sign = Signature.getInstance("SHA256withECDSA", "BC");
				factory = KeyFactory.getInstance("EC", "BC");
			}
		} 
		catch (Exception e) {
			throw new IllegalArgumentException("Unable to get Signature Instance");
		}

		PrivateKey pk = generatePrivateKey(factory, "priv-key.pem");

		try {
			sign.initSign(pk);
			sign.update(message);

			byte[] signature = sign.sign();

			return DatatypeConverter.printBase64Binary(signature);
		} catch (Exception e) {
			throw new IllegalArgumentException("Unable to sign the object:" + e.getMessage());
		}

	}

	//////////////////// Generate private key from file////////////////////
	private static PrivateKey generatePrivateKey(KeyFactory factory, String filename) {

		try {

			PemFile pemFile = new PemFile(filename);
			byte[] content = pemFile.getPemObject().getContent();
			PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(content);

			return factory.generatePrivate(privateKeySpec);
		} catch (Exception e) {
			throw new IllegalArgumentException("Error in generating private key:" + e.getMessage());
		}

	}

}

/*
 * private static PublicKey generatePublicKey(KeyFactory factory, String
 * filename) throws InvalidKeySpecException, FileNotFoundException, IOException
 * { PemFile pemFile = new PemFile(filename);
 * 
 * byte[] content = pemFile.getPemObject().getContent(); X509EncodedKeySpec
 * pubKeySpec = new X509EncodedKeySpec(content);
 * 
 * return factory.generatePublic(pubKeySpec); }
 */
