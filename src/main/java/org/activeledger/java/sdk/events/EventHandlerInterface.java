package org.activeledger.java.sdk.events;

public interface EventHandlerInterface {

	public void handleEvent(String message);
	public void onError(Throwable t);
	public void onOpen();
}
