package org.activeledger.java.sdk.transfer.funds;

import java.util.Map;

import org.activeledger.java.sdk.key.management.Encryption;

public class TransferFundsModel {

	private String namespace;
	private String contract;
	private String inputIdentity;
	private String outputIdentity;
	private String entry;
	private String inputIdentitySymbol;
	private Double amount;
	private Encryption encrp;
	private Map<String, String> signature;
	private boolean selfSign;

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

	public String getInputIdentity() {
		return inputIdentity;
	}

	public void setInputIdentity(String inputIdentity) {
		this.inputIdentity = inputIdentity;
	}

	public String getOutputIdentity() {
		return outputIdentity;
	}

	public void setOutputIdentity(String outputIdentity) {
		this.outputIdentity = outputIdentity;
	}

	public String getEntry() {
		return entry;
	}

	public void setEntry(String entry) {
		this.entry = entry;
	}

	public String getInputIdentitySymbol() {
		return inputIdentitySymbol;
	}

	public void setInputIdentitySymbol(String inputIdentitySymbol) {
		this.inputIdentitySymbol = inputIdentitySymbol;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Encryption getEncrp() {
		return encrp;
	}

	public void setEncrp(Encryption encrp) {
		this.encrp = encrp;
	}

	public Map<String, String> getSignature() {
		return signature;
	}

	public void setSignature(Map<String, String> signature) {
		this.signature = signature;
	}

	public boolean isSelfSign() {
		return selfSign;
	}

	public void setSelfSign(boolean selfSign) {
		this.selfSign = selfSign;
	}

}
