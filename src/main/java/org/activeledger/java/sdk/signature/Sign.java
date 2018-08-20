package org.activeledger.java.sdk.signature;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.Signature;

import javax.xml.bind.DatatypeConverter;

import org.activeledger.java.sdk.key.management.Encryption;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;


@Component("Sign")
public class Sign {

	final static Logger logger = Logger.getLogger(Sign.class);
	public static final String PRIVATE_KEY="priv-key.pem";

	public String signMessage(byte[] message, KeyPair keyPair, Encryption encrp) {
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
			logger.error("Unable to get Signature Instance",e);
			throw new IllegalArgumentException("Unable to get Signature Instance");
		}

		PrivateKey pk = keyPair.getPrivate();//generatePrivateKey(factory, "PRIVATE_KEY");

		try {
			sign.initSign(pk);
			sign.update(message);

			byte[] signature = sign.sign();

			return DatatypeConverter.printBase64Binary(signature);
		} catch (Exception e) {
			logger.error("Unable to sign the object",e);
			throw new IllegalArgumentException("Unable to sign the object:"+e.getMessage());
		}

	}

	//////////////////// Generate private key from file////////////////////
/*	private static PrivateKey generatePrivateKey(KeyFactory factory, String filename) {

		try {

			PemFile pemFile = new PemFile(filename);
			byte[] content = pemFile.getPemObject().getContent();
			PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(content);

			return factory.generatePrivate(privateKeySpec);
		} catch (Exception e) {
			logger.error("Error in generating private key",e);
			throw new IllegalArgumentException("Error in generating private key:" + e.getMessage());
		}

	}*/
	
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

}
