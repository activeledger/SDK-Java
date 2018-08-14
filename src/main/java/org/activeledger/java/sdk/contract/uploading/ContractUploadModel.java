package org.activeledger.java.sdk.contract.uploading;

import java.security.KeyPair;

import org.activeledger.java.sdk.key.management.Encryption;

public class ContractUploadModel {

	private String namespace;
	private String contract;
	private String Identity;
	private KeyPair keyPair;
	private String version;
	private String keyNamespace;
	private String name;
	private String smartContract;
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
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getKeyNamespace() {
		return keyNamespace;
	}
	public void setKeyNamespace(String keyNamespace) {
		this.keyNamespace = keyNamespace;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSmartContract() {
		return smartContract;
	}
	public void setSmartContract(String smartContract) {
		this.smartContract = smartContract;
	}
	public Encryption getEncrp() {
		return encrp;
	}
	public void setEncrp(Encryption encrp) {
		this.encrp = encrp;
	}
	
	
	
	
	
	
}
