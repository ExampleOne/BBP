package com.quinten.arce.game;

import com.quinten.arce.Reference;

public class Answer
{
	
	public Answer()
	{}
	
	public Answer(String answer)
	{
		this.text = answer;
	}
	
	public Answer(String answer, String imageLocation)
	{
		this.text = answer;
		this.imageLocation = imageLocation;
	}
	
	@Override
	public String toString()
	{
		return "Answer: " + text;
	}
	
	public String text = Reference.ANSWERS_UNAVAILABLE;
	public String imageLocation = "";
	public boolean isCorrect = false;
}
