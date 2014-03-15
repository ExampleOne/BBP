package com.quinten.arce.game;


public abstract class Occurable implements Runnable
{
	public abstract void occur();
	
	@Override
	public final void run()
	{
		try
		{
			Thread.sleep(time);
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		occur();
	}
	
	public long time = 1000;
}
