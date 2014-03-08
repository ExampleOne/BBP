package com.quinten.arce.game;

public enum Difficulty
{
	EASY("Easy", (byte) 1, (byte) 10), 
	MEDIUM("Medium", (byte) 5, (byte) 15), 
	HARD("Hard", (byte) 10, (byte) 20), 
	EXTREME("Extreme", (byte) 20, (byte) 30);
	
	Difficulty(String name, byte minDifficulty, byte maxDifficulty)
	{
		if(maxDifficulty < minDifficulty)
			throw new IllegalArgumentException("minDifficulty must be lower than maxDifficulty: minDifficulty = "
					+ minDifficulty + ", maxDifficulty = " + maxDifficulty);
		
		this.name = name;
		this.minDifficulty = minDifficulty;
		this.maxDifficulty = maxDifficulty;
	}
	
	public String getName()
	{
		return name;
	}
	
	public byte getMinDifficulty()
	{
		return minDifficulty;
	}
	
	public byte getMaxDifficulty()
	{
		return maxDifficulty;
	}
	
	public boolean isThisDifficulty(byte difficultyLevel)
	{
		return ((difficultyLevel >= minDifficulty) && (difficultyLevel <= maxDifficulty));
	}
	
	private String name;
	private byte minDifficulty;
	private byte maxDifficulty;
}
