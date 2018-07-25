package org.activeledger.java.sdk.key.management;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.SignatureException;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.InvalidKeySpecException;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.activeledger.java.sdk.onboard.OnboardIdentity;
import org.activeledger.java.sdk.onboard.Transaction;
import org.activeledger.java.sdk.utility.Utility;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.generators.ECKeyPairGenerator;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.util.PrivateKeyInfoFactory;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import sun.misc.BASE64Encoder;


@Component("keyGen")
public class KeyGen {
	@Autowired
	private OnboardIdentity onboardIdentiy;

	
	    public void generateKeyPair(Encryption encrp) throws NoSuchAlgorithmException, NoSuchProviderException, FileNotFoundException, IOException, InvalidKeyException, SignatureException, InvalidKeySpecException, CryptoException, InvalidAlgorithmParameterException {
	        
	    		Security.addProvider(new BouncyCastleProvider());
	    		AsymmetricCipherKeyPair keypair;
	    		KeyPair keyPair=null;
	    		if(encrp==Encryption.RSA) {
	    			keyPair = createRSAKeyPair();
	    		}
	    		else if(encrp==Encryption.EC){
	    			keyPair=createSecp256k1KeyPair();
	    		}
				PrivateKey priv=keyPair.getPrivate();
				PublicKey pub=keyPair.getPublic();

				System.out.println(Hex.toHexString(pub.getEncoded()));

				Utility.writePem("priv-key.pem","EC PRIVATE KEY",priv);
				Utility.writePem("pub-key.pem","PUBLIC KEY",pub);
				onBoardIdentity(keyPair,encrp);
	    }
	    
	    public void onBoardIdentity(KeyPair keyPair,Encryption encrp)
	    { 
	    	Transaction	transaction =onboardIdentiy.onboard(keyPair,encrp);

    		Gson gson = new Gson();

            
            String transactionJson = gson.toJson(transaction);
            try {
            	HttpClient httpclient = HttpClients.createDefault();
            	//HttpPost httppost = new HttpPost("http://35.195.221.172:5260");
            	HttpPost httppost = new HttpPost("http://127.0.0.1:5260");
            	StringEntity entity=new StringEntity(transactionJson);
            	entity.setContentType("application/json");
            	httppost.setEntity(entity);
            	HttpResponse response = httpclient.execute(httppost);
            	HttpEntity resp = response.getEntity();

            	if (resp != null) {
            		String responseAsString = EntityUtils.toString(response.getEntity());
            	    System.out.println(responseAsString);
            	}

            }
            catch(Exception e)
            {
            	e.printStackTrace();
            }
    	
	    }
		public KeyPair createSecp256k1KeyPair() throws NoSuchProviderException,NoSuchAlgorithmException, InvalidAlgorithmParameterException {

			 KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("ECDSA", "BC");
			 ECGenParameterSpec ecGenParameterSpec = new ECGenParameterSpec("secp256k1");
			 keyPairGenerator.initialize(ecGenParameterSpec,new SecureRandom());
			 return keyPairGenerator.generateKeyPair();
		}
	
		private KeyPair createRSAKeyPair() throws NoSuchProviderException,NoSuchAlgorithmException, InvalidAlgorithmParameterException {

			 KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA", "BC");
			 keyPairGenerator.initialize(2048);
			 return keyPairGenerator.generateKeyPair();
		}

		
	}

