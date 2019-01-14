/*
 * MIT License (MIT)
 * Copyright (c) 2018
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.activeledger.java.sdk.generic.transaction;

import java.util.Map;

import javax.ws.rs.DefaultValue;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
/*
 * 
 * This class represents the Transaction request object which will be sent to Activeledger
 * 
 * */
@JsonInclude(Include.NON_NULL)
public class Transaction {
	
	@JsonProperty("$territoriality")
	private String territoriality;
	@JsonProperty("$tx")
	private TxObject txObject;
	@DefaultValue("false")
	@JsonProperty("$selfsign")
	private boolean selfSign;
	@JsonProperty("$sigs")
	private Map<String, Object> signature;


	public String getTerritoriality() {
		return territoriality;
	}

	public void setTerritoriality(String territoriality) {
		this.territoriality = territoriality;
	}

	public TxObject getTxObject() {
		return txObject;
	}

	public void setTxObject(TxObject txObject) {
		this.txObject = txObject;
	}

	public boolean isSelfSign() {
		return selfSign;
	}

	public void setSelfSign(boolean selfSign) {
		this.selfSign = selfSign;
	}

	public Map<String, Object> getSignature() {
		return signature;
	}

	public void setSignature(Map<String, Object> signature) {
		this.signature = signature;
	}

}
