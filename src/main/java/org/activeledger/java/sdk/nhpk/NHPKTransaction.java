package org.activeledger.java.sdk.nhpk;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
@JsonInclude(Include.NON_NULL)
public class NHPKTransaction {

	@JsonProperty("$tx")	
	private NHPKTxObject txObject;
	@JsonProperty("$selfsign")
	private boolean selfSign;
	public boolean isSelfSign() {
		return selfSign;
	}
	public void setSelfSign(boolean selfSign) {
		this.selfSign = selfSign;
	}
	@JsonProperty("$sigs")
	private Map<String,String> signature;
	public NHPKTxObject getTxObject() {
		return txObject;
	}
	public void setTxObject(NHPKTxObject txObject) {
		this.txObject = txObject;
	}
	public Map<String, String> getSignature() {
		return signature;
	}
	public void setSignature(Map<String, String> signature) {
		this.signature = signature;
	}
		
}
