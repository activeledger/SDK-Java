package org.activeledger.java.sdk.signature;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.Signature;

import javax.xml.bind.DatatypeConverter;

import org.activeledger.java.sdk.key.management.Encryption;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component("Sign")
public class Sign {

	private static final Logger logger = Logger.getLogger(Sign.class);
	public static final String PRIVATE_KEY = "priv-key.pem";

	public String signMessage(byte[] message, KeyPair keyPair, Encryption encrp) {
		Signature sign = null;

		try {
			if (encrp == Encryption.RSA) {
				sign = Signature.getInstance("SHA256withRSA", "BC");
			} else if (encrp == Encryption.EC) {
				sign = Signature.getInstance("SHA256withECDSA", "BC");
			}
		} catch (Exception e) {
			logger.error("Unable to get Signature Instance", e);
			throw new IllegalArgumentException("Unable to get Signature Instance");
		}

		PrivateKey pk = keyPair.getPrivate(); // generatePrivateKey(factory, PRIVATE_KEY);

		try {
			sign.initSign(pk);
			sign.update(message);
			byte[] signature = sign.sign();
			return DatatypeConverter.printBase64Binary(signature);
		} catch (Exception e) {
			logger.error("Unable to sign the object", e);
			throw new IllegalArgumentException("Unable to sign the object:" + e.getMessage());
		}

	}

}
