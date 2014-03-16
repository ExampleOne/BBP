package com.quinten.arce.screens;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.quinten.arce.game.Question;

public class QuestionButton extends TextButton
{
	
	public QuestionButton(String text, Skin skin, Question question)
	{
		super(text, skin);
		initialise(question);
	}
	
	public QuestionButton(String text, TextButtonStyle style, Question question)
	{
		super(text, style);
		initialise(question);
	}
	
	public QuestionButton(String text, Skin skin, String styleName, Question question)
	{
		super(text, skin, styleName);
		initialise(question);
	}
	
	private void initialise(Question question)
	{
		
	}
	
	public Question getQuestion()
	{
		return question;
	}
	
	private Question question;
	
}
