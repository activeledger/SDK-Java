package org.activeledger.java.sdk.transfer.funds;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TransferFundsTransaction {

	@JsonProperty("$tx")	
	private TransferFundsTxObject txObject;
	@JsonProperty("$sigs")
	private Map<String,String> signature;
	public TransferFundsTxObject getTxObject() {
		return txObject;
	}
	public void setTxObject(TransferFundsTxObject txObject) {
		this.txObject = txObject;
	}
	public Map<String, String> getSignature() {
		return signature;
	}
	public void setSignature(Map<String, String> signature) {
		this.signature = signature;
	}
	
	
}
