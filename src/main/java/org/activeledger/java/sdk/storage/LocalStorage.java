package org.activeledger.java.sdk.storage;

import java.util.HashMap;
import java.util.Map;

public class LocalStorage {

	private static Map<String,Object> store;

	public static Map<String, Object> getStore() {
		if(store==null)
			store=new HashMap<>();
		return store;
	}

	public static void setStore(Map<String, Object> store) {
		LocalStorage.store = store;
	}
	
	public static void clearStorage()
	{
		LocalStorage.getStore().clear();
	}
}
