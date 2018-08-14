package org.activeledger.java.sdk.nhpk;

import java.security.KeyPair;

import org.activeledger.java.sdk.key.management.Encryption;

public class NHPKModel {

	private String namespace;
	private String contract;
	private String Identity;
	private KeyPair keyPair;
	private Encryption encrp;
	public String getNamespace() {
		return namespace;
	}
	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}
	public String getContract() {
		return contract;
	}
	public void setContract(String contract) {
		this.contract = contract;
	}
	public String getIdentity() {
		return Identity;
	}
	public void setIdentity(String identity) {
		Identity = identity;
	}
	public KeyPair getKeyPair() {
		return keyPair;
	}
	public void setKeyPair(KeyPair keyPair) {
		this.keyPair = keyPair;
	}
	public Encryption getEncrp() {
		return encrp;
	}
	public void setEncrp(Encryption encrp) {
		this.encrp = encrp;
	}
	
	
	
	
	
	
}
