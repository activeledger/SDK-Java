package org.activeledger.java.sdk.generic.transaction;

import java.util.Map;

import javax.ws.rs.DefaultValue;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonInclude(Include.NON_NULL)
public class Transaction {

	@JsonProperty("$tx")	
	private TxObject txObject;
	@DefaultValue("false")
	@JsonProperty("$selfsign")
	private boolean selfSign;
	@JsonProperty("$sigs")
	private Map<String,Object> signature;
	public TxObject getTxObject() {
		return txObject;
	}
	public void setTxObject(TxObject txObject) {
		this.txObject = txObject;
	}
	public boolean isSelfSign() {
		return selfSign;
	}
	public void setSelfSign(boolean selfSign) {
		this.selfSign = selfSign;
	}
	public Map<String, Object> getSignature() {
		return signature;
	}
	public void setSignature(Map<String, Object> signature) {
		this.signature = signature;
	}
	
	
	
	
	
	
	
}
