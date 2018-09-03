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
	private Integer keySize;

	ObjectMapper mapper;

	public KeyGen() {
		mapper = new ObjectMapper();
	}

	public KeyPair generateKeyPair(Encryption encrp)
			throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException, IOException {

		Security.addProvider(new BouncyCastleProvider());
		KeyPair keyPair = null;
		if (encrp == Encryption.RSA) {
			keyPair = createRSAKeyPair();
		} else if (encrp == Encryption.EC) {
			keyPair = createSecp256k1KeyPair();
		}

		return keyPair;
	}

	private KeyPair createSecp256k1KeyPair()
			throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException {
		logger.debug("Generating EC key pair");
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(env.getProperty("encryption.type.ec"), "BC");
		ECGenParameterSpec ecGenParameterSpec = new ECGenParameterSpec(env.getProperty("ec.curve"));
		keyPairGenerator.initialize(ecGenParameterSpec, new SecureRandom());
		return keyPairGenerator.generateKeyPair();
	}

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
