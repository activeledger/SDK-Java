package org.activeledger.java.sdk.contract.uploading;



import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
@JsonInclude(Include.NON_NULL)
public class ContractUploadingTransaction {

@JsonProperty("$tx")	
private ContractUploadingTxObject txObject;
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


public ContractUploadingTxObject getTxObject() {
	return txObject;
}
public void setTxObject(ContractUploadingTxObject txObject) {
	this.txObject = txObject;
}
public Map<String, String> getSignature() {
	return signature;
}
public void setSignature(Map<String, String> signature) {
	this.signature = signature;
}



}
