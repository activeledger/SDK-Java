package org.activeledger.java.sdk.transfer.funds;

import java.util.Map;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class TransferFundsTxObject {

	@JsonProperty("$namespace")
	private String namespace;
	@JsonProperty("$contract")
	private String contract;
	@JsonProperty("$entry")
	private String entry;
	@JsonProperty("$i")
	private Map<String,TransferFundsIdentity> inputIdentityList;
	@JsonProperty("$o")
	private Map<String,TransferFundsIdentity> outputIdentityList;
	@JsonProperty("$r")
	private Map<String,String> streamState;
	
	
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
	public String getEntry() {
		return entry;
	}
	public void setEntry(String entry) {
		this.entry = entry;
	}
	public Map<String, TransferFundsIdentity> getInputIdentityList() {
		return inputIdentityList;
	}
	public void setInputIdentityList(Map<String, TransferFundsIdentity> inputIdentityList) {
		this.inputIdentityList = inputIdentityList;
	}
	public Map<String, TransferFundsIdentity> getOutputIdentityList() {
		return outputIdentityList;
	}
	public void setOutputIdentityList(Map<String, TransferFundsIdentity> outputIdentityList) {
		this.outputIdentityList = outputIdentityList;
	}
	public Map<String, String> getStreamState() {
		return streamState;
	}
	public void setStreamState(Map<String, String> streamState) {
		this.streamState = streamState;
	}
	


	
	
	
	
	
}
