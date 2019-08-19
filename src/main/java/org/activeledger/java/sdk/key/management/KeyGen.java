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
package org.activeledger.java.sdk.key.management;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.ECGenParameterSpec;

import org.activeledger.java.sdk.storage.LocalStorage;
import org.apache.log4j.Logger;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component("KeyGen")
public class KeyGen {

	private static final  Logger logger = Logger.getLogger(KeyGen.class);

	@Autowired
	private Environment env;

	@Value("${rsa.keysize}")
	private Integer keySize;// default is 2048

	ObjectMapper mapper;

	public KeyGen() {
		mapper = new ObjectMapper();
	}
	/*
	 * Generate a pair of private an public key 
	 * input: Encryption type e.g. RSA or EC
	 * output: Key pair
	 */
	public KeyPair generateKeyPair(Encryption encrp)
			throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException, IOException {

		Security.addProvider(new BouncyCastleProvider());
		KeyPair keyPair = null;
		if (encrp == Encryption.RSA) {
			keyPair = createRSAKeyPair();
		} else if (encrp == Encryption.EC) {
			keyPair = createSecp256k1KeyPair();
		}
		LocalStorage.getStore().put("keyPair", keyPair);
		LocalStorage.getStore().put("type", encrp);
		
		return keyPair;
	}
	
	/*
	 * Create EC key pair. No need to call explicitly. generateKeyPair method will do it for you.
	 * output: Key pair
	 */
	private KeyPair createSecp256k1KeyPair()
			throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException {
		logger.debug("Generating EC key pair");
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(env.getProperty("encryption.type.ec"), "BC");
		ECGenParameterSpec ecGenParameterSpec = new ECGenParameterSpec(env.getProperty("ec.curve"));
		keyPairGenerator.initialize(ecGenParameterSpec, new SecureRandom());
		return keyPairGenerator.generateKeyPair();
	}
	
	/*
	 * Create RSA key pair. No need to call explicitly. generateKeyPair method will do it for you.
	 * output: Key pair
	 */
	private KeyPair createRSAKeyPair() throws NoSuchAlgorithmException, NoSuchProviderException {
		logger.debug("Generating RSA key pair" + getKeySize());
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(env.getProperty("encryption.type.rsa"), "BC");
		keyPairGenerator.initialize((getKeySize()));
		return keyPairGenerator.generateKeyPair();
	}

	public Integer getKeySize() {
		return keySize;
	}

	public void setKeySize(Integer keySize) {
		this.keySize = keySize;
	}

}
