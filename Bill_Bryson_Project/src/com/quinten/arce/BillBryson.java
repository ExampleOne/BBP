package com.quinten.arce;

import java.util.ArrayList;

import com.badlogic.gdx.Game;
import com.quinten.arce.game.Difficulty;
import com.quinten.arce.game.Question;
import com.quinten.arce.screens.Jeopardy;
import com.quinten.arce.screens.MainMenu;
import com.quinten.arce.screens.SplashScreen;
import com.quinten.arce.screens.SubMenuScreen;

/**
 * The main class in the project.
 * @author quinten
 *
 */
public class BillBryson extends Game 
{
	/**
	 * The start of the game.
	 */
	@Override
	public void create()
	{
		startLoading();
		setScreen(new SplashScreen(this));
		
		
	}
	
	private void startLoading()
	{
		Runnable load = new Runnable()
		{
			public void run()
			{
				loadQuestions();
			}
		};
		new Thread(load).start();
	}
	
	private void loadQuestions()
	{
		ArrayList<Question> questions = Question.getQuestions();
		Difficulty.EASY.setQuestions(filterQuestions(Difficulty.EASY, questions));
		Difficulty.MEDIUM.setQuestions(filterQuestions(Difficulty.MEDIUM, questions));
		Difficulty.HARD.setQuestions(filterQuestions(Difficulty.HARD, questions));
		Difficulty.EXTREME.setQuestions(filterQuestions(Difficulty.EXTREME, questions));
		
		try
		{
			Thread.currentThread().join();
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	
	private ArrayList<Question> filterQuestions(Difficulty difficulty, ArrayList<Question> questions)
	{
		ArrayList<Question> result = new ArrayList<Question>();
		
		for(Question question : questions)
		{
			if(difficulty.isThisDifficulty(question.getDifficultyLevel())) result.add(question);
		}
		
		return result;
	}
	
	public void splashScreenCompleted()
	{
		setScreen(new MainMenu(this));
	}
	
	public void subMenuScreen()
	{
		setScreen(new SubMenuScreen(this));
	}
	
	public void startGame(Difficulty difficulty)
	{
		setScreen(new Jeopardy(this, difficulty));		
	}

	@Override
	public void dispose() 
	{
		super.dispose();
	}

	@Override
	public void render() 
	{		
		super.render();
	}

	@Override
	public void resize(int width, int height) 
	{
		super.resize(width, height);
	}

	@Override
	public void pause() 
	{
		super.pause();
	}

	@Override
	public void resume() 
	{
		super.resume();
	}
}
