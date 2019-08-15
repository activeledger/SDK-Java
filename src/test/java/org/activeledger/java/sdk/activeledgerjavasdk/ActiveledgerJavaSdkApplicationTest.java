package org.activeledger.java.sdk.activeledgerjavasdk;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.sse.InboundSseEvent;
import javax.ws.rs.sse.SseEventSource;

import org.activeledger.java.sdk.key.management.Encryption;
import org.activeledger.java.sdk.key.management.KeyGen;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ActiveledgerJavaSdkApplicationTest {

	private AbstractApplicationContext ctx;
	private ObjectMapper mapper;
	private Map<String, String> map;

	public ActiveledgerJavaSdkApplicationTest() {
		mapper = new ObjectMapper();
		map = new HashMap<>();
	}

	@Before
	public void contextLoads() {
		ctx = ActiveledgerJavaSdkApplication.getContext();
		ActiveledgerJavaSdkApplication.setConnection("http", "testnet-uk.activeledger.io", "5260");
	}

	// Generate RSA keyPair
	public KeyPair generateRSAkeyPair()
			throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException, IOException {
		KeyGen keyGen = (KeyGen) ctx.getBean("KeyGen");
		KeyPair keyPair = keyGen.generateKeyPair(Encryption.RSA);
		return keyPair;
	}

	// Generate ECDSA KeyPair
	public KeyPair generateECDSAkeyPair()
			throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException, IOException {
		KeyGen keyGen = (KeyGen) ctx.getBean("KeyGen");
		KeyPair keyPair = keyGen.generateKeyPair(Encryption.EC);
		return keyPair;
	}
	

    private Client client;
    private WebTarget tut;

    @Before
    public void initClient() {
        this.client = ClientBuilder.newClient();
        this.tut = this.client.target("http://testnet-uk.activeledger.io:5259/api/activity/subscribe/33bbec76df05b1476aa47aa161b116681e3510049529df10cc3c03b41a00f786");
    }

	@Test
    public void init() throws IOException, InterruptedException {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://testnet-uk.activeledger.io:5261/api/activity/subscribe");
		SseEventSource source = SseEventSource.target(target).build() ;
		    source.register((inboundSseEvent) -> {this.onMessage(inboundSseEvent);});
		    source.open();
		    
		   
		
		
		
	Thread.sleep(30000);
        
        //block here, otherwise the test method will complete
    }

    void onMessage(InboundSseEvent event) {
    	System.out.println("---------In Message-----------");
        String id = event.getId();
        String name = event.getName();
        String payload = event.readData();
        String comment = event.getComment();
        System.out.println("id:"+id+"\nname:"+name+"\npayload:"+payload+"\ncomment:"+comment);
        //processing...
    }


	

}
