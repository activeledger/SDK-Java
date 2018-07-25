package org.activeledger.java.sdk.utility;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.Key;

import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemWriter;

public class Utility {

	private static PemObject pemObject;



	@Override
	public String toString() {
		return "PemFile [pemObject=" + pemObject + "]";
	}

	public static void writePem(String filename,String description,Key key) throws FileNotFoundException, IOException {
		PemWriter pemWriter = new PemWriter(new FileWriter(filename));
		try {
			pemObject = new PemObject(description, key.getEncoded());
			pemWriter.writeObject(pemObject);
		} finally {
			pemWriter.close();
		}

	}
	
	public static String readFileAsString(String fileName) throws IOException
	{
		InputStream is = new FileInputStream(fileName); 
		BufferedReader buf = new BufferedReader(new InputStreamReader(is)); 
		String line = buf.readLine(); 
		StringBuilder sb = new StringBuilder();
		while(line != null)
		{ 
			sb.append(line).append("\n"); 
			line = buf.readLine(); 
		} 
		String fileAsString = sb.toString();
		return fileAsString;
	}
}
