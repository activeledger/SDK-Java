package org.activeledger.java.sdk.activeledgerjavasdk;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;

import org.activeledger.java.sdk.key.management.Encryption;
import org.activeledger.java.sdk.key.management.KeyGen;
import org.bouncycastle.crypto.CryptoException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;


public class ActiveledgerJavaSdkApplication {

	public static void main(String[] args) throws IOException {

		AbstractApplicationContext  context = new AnnotationConfigApplicationContext(AppConfig.class);
        KeyGen keyGen = (KeyGen) context.getBean("keyGen");
        try {
			keyGen.generateKeyPair(Encryption.EC);
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchProviderException | SignatureException
				| InvalidKeySpecException | InvalidAlgorithmParameterException | CryptoException e) {
			
			e.printStackTrace();
			System.out.println("Exception in main:"+e.getMessage());
		}
         
        context.close();
	}
}
