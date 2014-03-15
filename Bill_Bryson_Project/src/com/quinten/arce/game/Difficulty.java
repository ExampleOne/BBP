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
	
	public static Difficulty highestDifficultyOf(byte difficultyLevel)
	{
		if(EXTREME.isThisDifficulty(difficultyLevel)) return EXTREME;
		else if(HARD.isThisDifficulty(difficultyLevel)) return HARD;
		else if(MEDIUM.isThisDifficulty(difficultyLevel)) return MEDIUM;
		else if(EASY.isThisDifficulty(difficultyLevel)) return EASY;
		else throw new IllegalArgumentException("Invalid difficulty level: " + difficultyLevel);
	}
	
	/**
	 * requires a sorted array.
	 * @return The index of the first occurrence of a question
	 *		of the specified difficulty.
	 */
	public static int findFirstOfDifficulty(Difficulty difficulty, byte difficultyLevel)
	{
		ArrayList<Question> questions = difficulty.getQuestions();
		int startIndex = 0;
		int endIndex = questions.size();
		int mid = (endIndex - startIndex) >>> 1;
		
		while(startIndex < endIndex)
		{
			mid = (endIndex - startIndex) >>> 1;
			if(questions.get(mid).getDifficultyLevel() == difficultyLevel)
			{
				while(questions.get(--mid).getDifficultyLevel() == difficultyLevel) {}
				return mid + 1;
			}
			if(questions.get(mid).getDifficultyLevel() > difficultyLevel)
			{
				startIndex = mid;
			} else
			{
				endIndex = mid;
			}
		}
		
		return -1;
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
	
	public byte getNumAnswers()
	{
		return numAnswers;
	}
	
	private String name;
	private byte minDifficulty; //inclusive
	private byte maxDifficulty; //inclusive
	private byte numAnswers;
	private ArrayList<Question> questions;
}
