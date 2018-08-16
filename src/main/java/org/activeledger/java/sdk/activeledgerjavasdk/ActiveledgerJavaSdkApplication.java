package org.activeledger.java.sdk.activeledgerjavasdk;

import java.io.IOException;

import org.activeledger.java.sdk.key.management.Encryption;
import org.activeledger.java.sdk.key.management.KeyGen;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;


public class ActiveledgerJavaSdkApplication {

	public static void main(String[] args) throws IOException, Base64DecodingException {

		AbstractApplicationContext  context = new AnnotationConfigApplicationContext(AppConfig.class);
        KeyGen keyGen = (KeyGen) context.getBean("keyGen");
        try {
			keyGen.generateKeyPair(Encryption.EC);
		} catch (Exception e) {
			
			e.printStackTrace();

		}
         
        context.close();
	}
	
	public static AbstractApplicationContext init()  {

		AbstractApplicationContext  context = new AnnotationConfigApplicationContext(AppConfig.class);
		
        return context;
	}
}
