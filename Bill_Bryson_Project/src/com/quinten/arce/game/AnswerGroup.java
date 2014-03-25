package com.quinten.arce.game;

import java.util.ArrayList;

public class AnswerGroup
{
	
	public AnswerGroup(Answer correctAnswer)
	{
		this.setCorrectAnswer(correctAnswer);
		addAnswer(correctAnswer);
	}
	
	public AnswerGroup(Answer correctAnswer, ArrayList<Answer> wrongAnswers)
	{
		this(correctAnswer);
		this.answers.addAll(wrongAnswers);
	}
	
	public void setCorrectAnswer(Answer correctAnswer)
	{
		if(this.correctAnswer != null)
		{
			addAnswer(correctAnswer);
			correctAnswer.isCorrect = true;
			this.correctAnswer.isCorrect = false;
			this.correctAnswer = correctAnswer;
		} else
		{
			correctAnswer.isCorrect = true;
			this.correctAnswer = correctAnswer;
		}
	}
	
	public Answer getCorrectAnswer()
	{
		return correctAnswer;
	}
	
	public void addAnswer(Answer answer)
	{
		if(!answers.contains(answer))answers.add(answer);
	}
	
	public ArrayList<Answer> getAnswers()
	{
		return answers;
	}
	
	public ArrayList<Answer> getWrongAnswers()
	{
		ArrayList<Answer> result = new ArrayList<Answer>(answers);
		result.remove(correctAnswer);
		return result;
	}
	
	private ArrayList<Answer> answers = new ArrayList<Answer>(4);
	private Answer correctAnswer;
}
