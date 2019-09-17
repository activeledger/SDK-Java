package org.activeledger.java.sdk.generic.transaction;

import java.security.KeyPair;

import org.activeledger.java.sdk.key.management.Encryption;


public class TxReqOptions {

	private String territoriaity;
	private boolean selfSign;
	private String stream;
	private KeyPair keyPair;
	private String keyName;
	private Encryption type;
	public Encryption getType() {
		return type;
	}
	public void setType(Encryption type) {
		this.type = type;
	}
	public String getTerritoriaity() {
		return territoriaity;
	}
	public void setTerritoriaity(String territoriaity) {
		this.territoriaity = territoriaity;
	}
	public boolean isSelfSign() {
		return selfSign;
	}
	public void setSelfSign(boolean selfSign) {
		this.selfSign = selfSign;
	}
	public String getStream() {
		return stream;
	}
	public void setStream(String stream) {
		this.stream = stream;
	}
	public KeyPair getKeyPair() {
		return keyPair;
	}
	public void setKeyPair(KeyPair keyPair) {
		this.keyPair = keyPair;
	}
	public String getKeyName() {
		return keyName;
	}
	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}
	
}
