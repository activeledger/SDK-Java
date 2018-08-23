package org.activeledger.java.sdk.onboard;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class OnboardTransaction {

@JsonProperty("$tx")	
private OnboardTxObject txObject;
@JsonProperty("$selfsign")
private boolean selfSign;
@JsonProperty("$sigs")
//private Map<String,String> signature;
private Map<String,String> signature;
public OnboardTxObject getTxObject() {
	return txObject;
}
public void setTxObject(OnboardTxObject txObject) {
	this.txObject = txObject;
}
public boolean isSelfSign() {
	return selfSign;
}
public void setSelfSign(boolean selfSign) {
	this.selfSign = selfSign;
}
public Map<String, String> getSignature() {
	return signature;
}
public void setSignature(Map<String, String> signature) {
	this.signature = signature;
}




}
