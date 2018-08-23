package org.activeledger.java.sdk.onboard;

import org.activeledger.java.sdk.key.management.Encryption;

public class Identity {


	private String publicKey;
	private Encryption type;
	
	
	public Encryption getType() {
		return type;
	}
	public void setType(Encryption type) {
		this.type = type;
	}
	public String getPublicKey() {
		return publicKey;
	}
	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	
	
}
