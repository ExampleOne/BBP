package com.quinten.arce;

import java.util.ArrayList;

import com.badlogic.gdx.Game;
import com.quinten.arce.debug.Stopwatch;
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
		Stopwatch watch = new Stopwatch();
		watch.startTimer();
		ArrayList<Question> questions = new ArrayList<Question>();
		
		try
		{
			questions = Question.getQuestions();
		} catch(IllegalArgumentException exception)
		{
			exception.printStackTrace();
			System.exit(2);
		}
		
		Difficulty.EASY.setQuestions(filterQuestions(Difficulty.EASY, questions));
		Difficulty.MEDIUM.setQuestions(filterQuestions(Difficulty.MEDIUM, questions));
		Difficulty.HARD.setQuestions(filterQuestions(Difficulty.HARD, questions));
		Difficulty.EXTREME.setQuestions(filterQuestions(Difficulty.EXTREME, questions));
		
		watch.endTimer("for loading questions");
		
		try
		{
			Thread.currentThread().join();
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	
	private  static ArrayList<Question> filterQuestions(Difficulty difficulty, ArrayList<Question> questions)
	{
		ArrayList<Question> result = new ArrayList<Question>();
		
		for(Question question : questions)
		{
			if(difficulty.isThisDifficulty(question.getDifficultyLevel())) result.add(question);
		}
		
		sortQuestionsByDifficulty(questions);
		return result;
	}
	
	private static ArrayList<Question> sortQuestionsByDifficulty(ArrayList<Question> questions)
	{
		int indexOfLargest;
		for(int i = 0; i < questions.size(); i++)
		{
			indexOfLargest = i;
			for(int j = i; j < questions.size(); j++)
			{
				if(questions.get(indexOfLargest).getDifficultyLevel() < questions.get(j).getDifficultyLevel()) indexOfLargest = j;
			}
			if(indexOfLargest != i) swap(questions, i, indexOfLargest);
		}
		return questions;
	}
	
	public static <T> void swap(ArrayList<T> list, int firstIndex, int secondIndex)
	{
		T temp = list.get(secondIndex);
		list.set(secondIndex, list.get(firstIndex));
		list.set(firstIndex, temp);
	}
	
	public void splashScreenCompleted()
	{
		setScreen(new MainMenu(this));
	}
	
	public void subMenuScreen()
	{
		stopwatch.startTimer();
		getScreen().dispose();
		setScreen(new SubMenuScreen(this));
		stopwatch.endTimer("between switching screens");
	}
	
	public void startGame(Difficulty difficulty)
	{
		stopwatch.startTimer();
		getScreen().dispose();
		setScreen(new Jeopardy(this, difficulty));
		stopwatch.endTimer("between switching screens");
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
	
	private Stopwatch stopwatch = new Stopwatch();
}
