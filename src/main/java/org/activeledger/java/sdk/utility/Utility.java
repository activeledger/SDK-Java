package org.activeledger.java.sdk.utility;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.Key;

import org.apache.log4j.Logger;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemWriter;

public class Utility {

	private static PemObject pemObject;
	final static Logger logger = Logger.getLogger(Utility.class);


	@Override
	public String toString() {
		return "PemFile [pemObject=" + pemObject + "]";
	}

	public static void writePem(String filename,String description,Key key) throws IOException  {
			PemWriter pemWriter=null;
			pemWriter = new PemWriter(new FileWriter(filename));
			pemObject = new PemObject(description, key.getEncoded());
			pemWriter.writeObject(pemObject);
		
			pemWriter.close();
		
	}
	
	public static String readFileAsString(String fileName)
	{
		try {
		InputStream is = new FileInputStream(fileName); 
		BufferedReader buf = new BufferedReader(new InputStreamReader(is)); 
		String line = buf.readLine(); 
		StringBuilder sb = new StringBuilder();
		while(line != null)
		{ 
			sb.append(line).append("\n"); 
			line = buf.readLine(); 
		} 
		buf.close();
		String fileAsString = sb.toString();
		return fileAsString;
		}catch(IOException e)
		{
			logger.error("Exception occurred while reading PEM file",e);
			throw new IllegalArgumentException("Exception occurred while reading PEM file:"+e.getMessage());
		}
	}
}
