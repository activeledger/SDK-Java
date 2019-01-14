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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
/*
 * this class is a representation of Transaction object within a transaction
 */
@JsonInclude(Include.NON_NULL)
public class TxObject {

	@JsonProperty("$namespace")
	private String namespace;
	@JsonProperty("$contract")
	private String contract;
	@JsonProperty("$entry")
	private String entry;
	@JsonProperty("$i")
	private Map<String, Object> inputIdentity;
	@JsonProperty("$o")
	private Map<String, Object> outputIdentity;
	@JsonProperty("$r")
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
