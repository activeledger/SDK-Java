package org.activeledger.java.sdk.contract.uploading;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ContractUploadingTxObject {

	@JsonProperty("$namespace")
	private String namespace;
	@JsonProperty("$contract")
	private String contract;
	@JsonProperty("$i")
	private Map<String, ContractUploadingIdentityList> identityList;
	@JsonProperty("$r")
	private Map<String, String> streamState;

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

	public Map<String, ContractUploadingIdentityList> getIdentityList() {
		return identityList;
	}

	public void setIdentityList(Map<String, ContractUploadingIdentityList> identityList) {
		this.identityList = identityList;
	}

	public Map<String, String> getStreamState() {
		return streamState;
	}

	public void setStreamState(Map<String, String> streamState) {
		this.streamState = streamState;
	}

}
