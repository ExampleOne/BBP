package com.quinten.arce.debug;

import com.badlogic.gdx.Gdx;
import com.quinten.arce.Reference;

public class Stopwatch
{
	
	public Stopwatch()
	{}
	

	/**
	 * Logs the current time in nano seconds.
	 */
	public static final void logNanoTime()
	{
		Gdx.app.log(Reference.LOG_TIME, "Nano time: " + System.nanoTime() + "ns");
	}
	
	/**
	 * logs the current time in Milli seconds.
	 */
	public static final void logTime()
	{
		Gdx.app.log(Reference.LOG_TIME, "Milli time: " + System.currentTimeMillis() + "ms");
	}
	
	public final void startTimer() 
	{
		time = System.currentTimeMillis();
	}
	
	public final void endTimer(String message) 
	{
		Gdx.app.log(Reference.LOG_TIME, "Time passed " + message + ": " + (System.currentTimeMillis() - time) + "ms");
		time = System.currentTimeMillis();
	}
	
	private long time;
	
}
