package org.activeledger.java.sdk.onboard;

import java.util.Map;

public class OnboardModel {

	private OnboardTxObjectModel txObject;
	private boolean selfSign;
	private Map<String, String> signature;
	// private String publicKey;

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

	public OnboardTxObjectModel getTxObject() {
		return txObject;
	}

	public void setTxObject(OnboardTxObjectModel txObject) {
		this.txObject = txObject;
	}

}
