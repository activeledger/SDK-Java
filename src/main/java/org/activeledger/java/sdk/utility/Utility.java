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
package org.activeledger.java.sdk.utility;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.Key;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemWriter;
import org.json.JSONArray;
import org.json.JSONObject;

import net.minidev.json.parser.ParseException;
/*
 * Utility class with some helper methods
 */
public class Utility {

	private static PemObject pemObject;
	private static final Logger logger = Logger.getLogger(Utility.class);
	public static final String FILENAME = "pub-key.pem";
	public static final String DESC = "PUBLIC KEY";
	
	@Override
	public String toString() {
		return "PemFile [pemObject=" + pemObject + "]";
	}

	private static void writePem(String filename, String description, Key key) throws IOException {
		PemWriter pemWriter = null;
		pemWriter = new PemWriter(new FileWriter(filename));
		pemObject = new PemObject(description, key.getEncoded());
		pemWriter.writeObject(pemObject);

		pemWriter.close();

	}

	private static String readFileAsString(String fileName) {
		try {
			InputStream is = new FileInputStream(fileName);
			BufferedReader buf = new BufferedReader(new InputStreamReader(is));
			String line = buf.readLine();
			StringBuilder sb = new StringBuilder();
			while (line != null) {
				sb.append(line).append("\n");
				line = buf.readLine();
			}
			buf.close();
			String fileAsString = sb.toString();
			return fileAsString;
		} catch (IOException e) {
			logger.error("Exception occurred while reading PEM file", e);
			throw new IllegalArgumentException("Exception occurred while reading PEM file:" + e.getMessage());
		}
	}

	public static String convertToStringPemFormat(Key key) {
		try {
			writePem(FILENAME, DESC, key);
			return readFileAsString(FILENAME);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("Unable to write pem file", e);
			throw new IllegalArgumentException("Unable to write pem file", e);
		}

	}

}
