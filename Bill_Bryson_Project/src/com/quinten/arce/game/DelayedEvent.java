package com.quinten.arce.game;


public class DelayedEvent
{
	
	public DelayedEvent(long time)
	{
		this.time = time;
	}
	
	public DelayedEvent(long time, Occurable occurable)
	{
		this.time = time;
		this.occurable = occurable;
	}
	
	public <T> void start(Occurable occurable)
	{
		this.occurable = occurable;
		occurable.time = this.time;
		new Thread(occurable).start();
	}
	
	public <T> void start()
	{
		if(occurable == null) throw new NullPointerException("Occurable event cannot be null!");
		occurable.time = this.time;
		new Thread(occurable).start();
	}
	
	private Occurable occurable;
	private long time;
}
