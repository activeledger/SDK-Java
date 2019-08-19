package org.activeledger.java.sdk.events;


import java.net.URI;
import java.util.concurrent.TimeUnit;

import com.launchdarkly.eventsource.EventHandler;
import com.launchdarkly.eventsource.EventSource;


public class EventSubscription {
	
	public void subscribe(String host,String port,EventHandlerInterface eventHandler) throws InterruptedException, InstantiationException, IllegalAccessException, ClassNotFoundException 
	{
		
		
		EventHandler eh = new SSEHandler(eventHandler);

	    String url = String.format("http://"+host+":"+port+"/api/activity/subscribe");
	    EventSource.Builder builder = new EventSource.Builder(eh, URI.create(url));
		builder.connectTimeoutMs(120000);
		builder.readTimeoutMs(120000);
	    EventSource eventSource = builder.build() ;
		eventSource.start();
		
		TimeUnit.MINUTES.sleep(10);
		
		
	}
	
	public void subscribe(String stream,String host,String port,EventHandlerInterface eventHandler) throws InterruptedException
	{
	
		
		EventHandler eh = new SSEHandler(eventHandler);
	    String url = String.format("http://"+host+":"+port+"/api/activity/subscribe/"+stream);
	    EventSource.Builder builder = new EventSource.Builder(eh, URI.create(url));
		builder.connectTimeoutMs(120000);
		builder.readTimeoutMs(120000);
	    EventSource eventSource = builder.build() ;
		eventSource.start();
	} 
	
	public void eventSubscribe(String contract,String event,String host,String port,EventHandlerInterface eventHandler) throws InterruptedException
	{
		EventHandler eh = new SSEHandler(eventHandler);
	    String url = String.format("http://"+host+":"+port+"/api/events/"+contract+"/"+event);
	    EventSource.Builder builder = new EventSource.Builder(eh, URI.create(url));
		builder.connectTimeoutMs(120000);
		builder.readTimeoutMs(120000);
	    EventSource eventSource = builder.build() ;
		eventSource.start();
	}
	public void eventSubscribe(String contract,String host,String port,EventHandlerInterface eventHandler) throws InterruptedException
	{

		EventHandler eh = new SSEHandler(eventHandler);
	    String url = String.format("http://"+host+":"+port+"/api/events/"+contract);
	    EventSource.Builder builder = new EventSource.Builder(eh, URI.create(url));
		builder.connectTimeoutMs(120000);
		builder.readTimeoutMs(120000);
	    EventSource eventSource = builder.build() ;
		eventSource.start();
	}
	public void eventSubscribe(String host,String port,EventHandlerInterface eventHandler) throws InterruptedException
	{
		
		EventHandler eh = new SSEHandler(eventHandler);
	    String url = String.format("http://"+host+":"+port+"/api/events/");
	    EventSource.Builder builder = new EventSource.Builder(eh, URI.create(url));
		builder.connectTimeoutMs(120000);
		builder.readTimeoutMs(120000);
	    EventSource eventSource = builder.build() ;
		eventSource.start();
	}
}
