package com.quinten.arce.game;

public enum Catagory
{
	PHYSICS("Physics"),
	CHEMISTRY("Chemistry"),
	BIOLOGY("Biology");
	
	Catagory(String name)
	{
		this.name = name;
	}
	
	public static Catagory deserialise(String string)
	{
		if(string.equalsIgnoreCase(PHYSICS.name)) return PHYSICS;
		else if(string.equalsIgnoreCase(CHEMISTRY.name)) return CHEMISTRY;
		else if(string.equalsIgnoreCase(BIOLOGY.name)) return BIOLOGY;
		
		throw new IllegalArgumentException("Cannot identify catagory named: " + string);
	}
	
	public String getName()
	{
		return name;
	}
	
	private String name;
}
