package org.activeledger.java.sdk.activeledgerjavasdk;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;


public class ActiveledgerJavaSdkApplication {
/*
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
	*/
	public static AbstractApplicationContext init()  {

		AbstractApplicationContext  context = new AnnotationConfigApplicationContext(AppConfig.class);
		
        return context;
	}
}
