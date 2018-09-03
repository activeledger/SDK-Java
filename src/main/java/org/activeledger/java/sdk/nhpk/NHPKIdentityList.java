package org.activeledger.java.sdk.nhpk;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NHPKIdentityList {

	@JsonProperty("$nhpk")
	private String nhpk;

	public String getNhpk() {
		return nhpk;
	}

	public void setNhpk(String nhpk) {
		this.nhpk = nhpk;
	}

}
