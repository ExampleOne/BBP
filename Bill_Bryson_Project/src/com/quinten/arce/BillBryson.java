package com.quinten.arce;

import java.io.FileNotFoundException;

import com.badlogic.gdx.Game;
import com.quinten.arce.game.Question;
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
		startDisplaying();
		loadQuestions();
	}
	
	private void startDisplaying()
	{
		final BillBryson me = this;
		Runnable displayScreen = new Runnable()
		{
			@Override
			public void run()
			{
				setScreen(new SplashScreen(me));
			}
		};
		new Thread(displayScreen).run();
	}
	
	private void loadQuestions()
	{
		Question[] questions = Question.getQuestions();
	}
	
	public void splashScreenCompleted()
	{
		setScreen(new MainMenu(this));
	}
	
	public void subMenuScreen()
	{
		setScreen(new SubMenuScreen(this));
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
	
	private Question[] easyQuestions;
	private Question[] meduimQuestions;
	private Question[] hardQuestions;
	private Question[] extremeQuestions;
}
