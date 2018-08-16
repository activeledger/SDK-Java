package org.activeledger.java.sdk.onboard;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OnboardTxObject {

	@JsonProperty("$namespace")
	private String namespace;
	@JsonProperty("$contract")
	private String contract;
	@JsonProperty("$i")
	private OnboardIdentityList identityList;
	
	
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
	public OnboardIdentityList getIdentityList() {
		return identityList;
	}
	public void setIdentityList(OnboardIdentityList identityList) {
		this.identityList = identityList;
	}

}
