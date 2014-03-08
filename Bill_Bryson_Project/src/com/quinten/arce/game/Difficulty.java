package com.quinten.arce.game;

import java.util.ArrayList;

public enum Difficulty
{
	EASY("Easy", (byte) 1, (byte) 10, (byte) 4), 
	MEDIUM("Medium", (byte) 5, (byte) 15, (byte) 4), 
	HARD("Hard", (byte) 10, (byte) 20, (byte) 5), 
	EXTREME("Extreme", (byte) 20, (byte) 30, (byte) 6);
	
	Difficulty(String name, byte minDifficulty, byte maxDifficulty, byte numQuestions)
	{
		if(maxDifficulty < minDifficulty)
			throw new IllegalArgumentException("minDifficulty must be lower than maxDifficulty: minDifficulty = "
					+ minDifficulty + ", maxDifficulty = " + maxDifficulty);
		
		this.name = name;
		this.minDifficulty = minDifficulty;
		this.maxDifficulty = maxDifficulty;
		this.numAnswers = numQuestions;
	}
	
	public static Difficulty deserialise(String string)
	{
		if(string.equalsIgnoreCase(EASY.name)) return EASY;
		else if(string.equalsIgnoreCase(MEDIUM.name)) return MEDIUM;
		else if(string.equalsIgnoreCase(HARD.name)) return HARD;
		else if(string.equalsIgnoreCase(EXTREME.name)) return EXTREME;
		
		throw new IllegalArgumentException("Cannot identify difficulty named: " + string);
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
	
	public void setQuestions(ArrayList<Question> questions)
	{
		this.questions = questions;
	}
	
	public ArrayList<Question> getQuestions()
	{
		return questions;
	}
	
	private String name;
	private byte minDifficulty;
	private byte maxDifficulty;
	private byte numAnswers;
	private ArrayList<Question> questions;
}
