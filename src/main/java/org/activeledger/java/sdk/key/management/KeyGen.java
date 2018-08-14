package org.activeledger.java.sdk.key.management;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.ECGenParameterSpec;

import org.activeledger.java.sdk.onboard.OnboardIdentity;
import org.activeledger.java.sdk.onboard.OnboardIdentityReq;
import org.activeledger.java.sdk.onboard.OnboardTransaction;
import org.activeledger.java.sdk.utility.Utility;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;


@Component("keyGen")
public class KeyGen {
	@Autowired
	private OnboardIdentity onboardIdentiy;
	ObjectMapper mapper;
	
		public KeyGen()
		{
			mapper=new ObjectMapper();
		}

	
	    public KeyPair generateKeyPair(Encryption encrp) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException, IOException {
	        
	    		Security.addProvider(new BouncyCastleProvider());
	    		KeyPair keyPair=null;
	    		if(encrp==Encryption.RSA) {
	    			keyPair = createRSAKeyPair();
	    		}
	    		else if(encrp==Encryption.EC){
	    			keyPair=createSecp256k1KeyPair();
	    		}
				PrivateKey priv=keyPair.getPrivate();
				PublicKey pub=keyPair.getPublic();
				
				if(encrp==Encryption.RSA)
				{
					Utility.writePem("priv-key.pem","RSA PRIVATE KEY",priv);
				}
				else
				{
					Utility.writePem("priv-key.pem","EC PRIVATE KEY",priv);
				}
				Utility.writePem("pub-key.pem","PUBLIC KEY",pub);
				//onboardIdentiy.onBoardIdentity(keyPair,encrp);
				return keyPair;
	    }
	    
	    
		public KeyPair createSecp256k1KeyPair() throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException {

			 KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("ECDSA", "BC");
			 ECGenParameterSpec ecGenParameterSpec = new ECGenParameterSpec("secp256k1");
			 keyPairGenerator.initialize(ecGenParameterSpec,new SecureRandom());
			 return keyPairGenerator.generateKeyPair();
		}
	
		private KeyPair createRSAKeyPair() throws NoSuchAlgorithmException, NoSuchProviderException  {

			 KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA", "BC");
			 keyPairGenerator.initialize(2048);
			 return keyPairGenerator.generateKeyPair();
		}

		
	}

