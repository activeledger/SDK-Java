package org.activeledger.java.sdk.events;

import com.launchdarkly.eventsource.EventHandler;
import com.launchdarkly.eventsource.MessageEvent;

public class SSEHandler implements EventHandler {

	EventHandlerInterface test;
	public SSEHandler(EventHandlerInterface test)
	{
		this.test=test;
	}
	
	@Override
	public void onOpen() throws Exception {
		//System.out.println("onOpen");
		test.onOpen();
	}

	@Override
	public void onClosed() throws Exception {
		//System.out.println("onClosed");
	}

	@Override
	public void onMessage(String event, MessageEvent messageEvent) throws Exception {
		
		test.handleEvent(messageEvent.getData());
		
		
	}
	
	@Override
	public void onComment(String comment) throws Exception {
		//System.out.println("onComment");
	}

	@Override
	public void onError(Throwable t) {
		test.onError(t);
	}


}
