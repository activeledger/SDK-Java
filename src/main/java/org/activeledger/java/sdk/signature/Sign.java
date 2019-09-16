/*
 * MIT License (MIT)
 * Copyright (c) 2018
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
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
/*
 * Sign the transaction for authentication
 * input: i) Transaction that you want to sign
 *        ii) keypair to use for signing
 *        iii) Encryption type to use
 * output: Signature 
 */
	public String signMessage(byte[] message, KeyPair keyPair, Encryption encrp) {
		Signature sign = null;
		//KeyFactory factory = null;
		try {
			if (encrp == Encryption.RSA) {
				sign = Signature.getInstance("SHA256withRSA", "BC");
				//factory = KeyFactory.getInstance("RSA", "BC");
				} else if (encrp == Encryption.EC) {
				sign = Signature.getInstance("SHA256withECDSA", "BC");
			}
		} catch (Exception e) {
			logger.error("Unable to get Signature Instance", e);
			throw new IllegalArgumentException("Unable to get Signature Instance");
		}

		PrivateKey pk = keyPair.getPrivate(); 
		//PrivateKey pk =  generatePrivateKey(factory, PRIVATE_KEY);

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
	
	/*private static PrivateKey generatePrivateKey(KeyFactory factory, String filename) {
		try {
			Utility pemFile = new Utility(filename);
			byte[] content = pemFile.getPemObject().getContent();
			PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(content);
			return factory.generatePrivate(privateKeySpec);
		} catch (Exception e) {
			logger.error("Error in generating private key",e);
			throw new IllegalArgumentException("Error in generating private key:" + e.getMessage());
		}
	}*/

}
