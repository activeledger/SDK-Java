package org.activeledger.java.sdk.onboard;

import java.util.Map;

public class OnboardTxObjectModel {

	private String namespace;
	private String contract;
	private Map<String, Identity> Identity;

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

	public Map<String, Identity> getIdentity() {
		return Identity;
	}

	public void setIdentity(Map<String, Identity> identity) {
		Identity = identity;
	}

}
