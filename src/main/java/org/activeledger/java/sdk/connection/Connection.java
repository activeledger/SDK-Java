package org.activeledger.java.sdk.connection;

public class Connection {

	private static String protocol;
	private static String url;
	private static String port;
	
	private Connection() {
	}

	public static String getProtocol() {
		return protocol;
	}

	public static void setProtocol(String protocol) {
		Connection.protocol = protocol;
	}

	public static String getUrl() {
		return url;
	}

	public static void setUrl(String url) {
		Connection.url = url;
	}

	public static String getPort() {
		return port;
	}

	public static void setPort(String port) {
		Connection.port = port;
	}

	public static String getConnectionURL() {
		return getProtocol() + "://" + getUrl() + ":" + getPort();
	}

}
