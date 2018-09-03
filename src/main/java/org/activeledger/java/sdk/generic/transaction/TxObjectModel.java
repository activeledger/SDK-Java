package org.activeledger.java.sdk.generic.transaction;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class TxObjectModel {

	private String namespace;
	private String contract;
	private String entry;
	private Map<String, Object> inputIdentity;

	private Map<String, Object> outputIdentity;

	private Map<String, Object> streamState;

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

	public Map<String, Object> getInputIdentity() {
		return inputIdentity;
	}

	public void setInputIdentity(Map<String, Object> inputIdentity) {
		this.inputIdentity = inputIdentity;
	}

	public Map<String, Object> getOutputIdentity() {
		return outputIdentity;
	}

	public void setOutputIdentity(Map<String, Object> outputIdentity) {
		this.outputIdentity = outputIdentity;
	}

	public Map<String, Object> getStreamState() {
		return streamState;
	}

	public void setStreamState(Map<String, Object> streamState) {
		this.streamState = streamState;
	}

	public String getEntry() {
		return entry;
	}

	public void setEntry(String entry) {
		this.entry = entry;
	}

}
