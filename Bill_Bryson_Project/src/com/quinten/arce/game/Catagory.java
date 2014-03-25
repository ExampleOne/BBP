package com.quinten.arce.game;


public enum Catagory
{
	PHYSICS("Physics"),
	CHEMISTRY("Chemistry"),
	BIOLOGY("Biology"),
	MISC("Misc");
	
	Catagory(String name)
	{
		this.name = name;
	}
	
	public static Catagory deserialise(String string)
	{
		if(string.equalsIgnoreCase(PHYSICS.name)) return PHYSICS;
		else if(string.equalsIgnoreCase(CHEMISTRY.name)) return CHEMISTRY;
		else if(string.equalsIgnoreCase(BIOLOGY.name)) return BIOLOGY;
		else if(string.equalsIgnoreCase(MISC.name)) return MISC;
		
		throw new IllegalArgumentException("Cannot identify catagory named: " + string);
	}
	
	public String getName()
	{
		return name;
	}
	
	public byte getId()
	{
		return id;
	}
	
	private String name;
	private byte id;
	
	private static byte count = 0;
	
	static
	{
		for(Catagory catagory : Catagory.values())
		{
			catagory.id = count++;
		}
	}
}