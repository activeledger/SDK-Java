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
package org.activeledger.java.sdk.activeledgerjavasdk;

import org.activeledger.java.sdk.connection.Connection;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

public class ActiveledgerJavaSdkApplication {
	private static AbstractApplicationContext context;

	private ActiveledgerJavaSdkApplication() {
	}

	/*
	 * Initialising application with configurations. Don't need to call explicitly. 
	 * getContext() below will do the work for you
	 */
	public static AbstractApplicationContext init() {

		context = new AnnotationConfigApplicationContext(AppConfig.class);

		return context;
	}
	
	/*
	 * Set connection, needed to set only once. After that Connection.getConnectionURL() can be used.
	 * input: protocol e.g hhtp or https, url of the network, port of the network
	 */
	public static void setConnection(String protocol, String url, String port) {

		Connection.setProtocol(protocol);
		Connection.setUrl(url);
		Connection.setPort(port);

	}

	/*
	 * Call when needed to get application context. Usually required when initialising a bean.
	 */
	public static AbstractApplicationContext getContext() {
		if (context == null) {
			context = ActiveledgerJavaSdkApplication.init();
		}
		return context;
	}
}
