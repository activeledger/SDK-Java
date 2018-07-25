package org.activeledger.java.sdk.onboard;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

import org.activeledger.java.sdk.key.management.Encryption;
import org.activeledger.java.sdk.utility.PemFile;
import org.activeledger.java.sdk.utility.Utility;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import sun.misc.BASE64Encoder;

@Component
public class OnboardIdentity {

	public Transaction onboard(KeyPair keyPair, Encryption encrp) {
		Transaction transaction = new Transaction();
		IdentityList i = new IdentityList();
		Identity identity = new Identity();
		if (encrp == Encryption.RSA)
			identity.setType("rsa");
		else if (encrp == Encryption.EC)
			identity.setType("secp256k1");

		String pubKey = null;
		try {
			pubKey = Utility.readFileAsString("pub-key.pem");
		} catch (IOException e) {
		
			e.printStackTrace();
		}

		identity.setPublicKey(pubKey);

		i.setIdentity(identity);
		Tx tx = new Tx();
		tx.setIdentity(i);

		tx.set$contract("onboard");
		tx.set$namespace("default");
		transaction.set$tx(tx);
		Gson gson = new Gson();
		String json = gson.toJson(tx);

		org.activeledger.java.sdk.onboard.Signature sig = new org.activeledger.java.sdk.onboard.Signature();
		try {
				sig.setIdentity(signMessage(json.getBytes(), keyPair, encrp));
		} catch (Exception e) {
			
			e.printStackTrace();
		}

		transaction.set$selfsign(true);
		transaction.setSig(sig);
		
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
				sign = Signature.getInstance("NONEwithECDSA", "BC");
				factory = KeyFactory.getInstance("EC", "BC");
			}

			BASE64Encoder encoder = new BASE64Encoder();

			
			PrivateKey pk=null;
			if(encrp == Encryption.RSA)
			{
			pk=generatePrivateKey(factory, "priv-key.pem");
			//pk=keyPair.getPrivate();
			}
			else if(encrp == Encryption.EC) {
			
			pk=generatePrivateKey(factory, "priv-key.pem");
			//pk=keyPair.getPrivate();
			}
			sign.initSign(pk);
			sign.update(message);
			byte[] signature = sign.sign();


			return encoder.encode(signature);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	
	}

	//////////////////// helper functions////////////////////
	private static PrivateKey generatePrivateKey(KeyFactory factory, String filename)
			throws InvalidKeySpecException, FileNotFoundException, IOException {
		PemFile pemFile = new PemFile(filename);
		byte[] content = pemFile.getPemObject().getContent();
	    PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(content); 

		return factory.generatePrivate(privateKeySpec);
	}

	/*private static PublicKey generatePublicKey(KeyFactory factory, String filename)
			throws InvalidKeySpecException, FileNotFoundException, IOException {
		PemFile pemFile = new PemFile(filename);

		byte[] content = pemFile.getPemObject().getContent();
		X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(content);

		return factory.generatePublic(pubKeySpec);
	}*/

	    
	/*public static String toHex(String arg) {
	        return String.format("%040x", new BigInteger(1, arg.getBytes(StandardCharsets.UTF_8)));
	}*/
}
