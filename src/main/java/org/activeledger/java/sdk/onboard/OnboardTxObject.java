package org.activeledger.java.sdk.onboard;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class OnboardTxObject {

	@JsonProperty("$namespace")
	private String namespace;
	@JsonProperty("$contract")
	private String contract;
	@JsonProperty("$i")
	private Map<String,Identity> identityList;
	
	
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
	public Map<String, Identity> getIdentityList() {
		return identityList;
	}
	public void setIdentityList(Map<String, Identity> identityList) {
		this.identityList = identityList;
	}


}
