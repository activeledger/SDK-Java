package org.activeledger.java.sdk.onboard;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OnboardTransaction {

@JsonProperty("$tx")	
private OnboardTxObject txObject;
@JsonProperty("$selfsign")
private boolean selfSign;
@JsonProperty("$sigs")
private Signature signature;

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
public Signature getSignature() {
	return signature;
}
public void setSignature(Signature signature) {
	this.signature = signature;
}

}
