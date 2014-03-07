package com.quinten.bindIt;

import com.badlogic.gdx.Game;
import com.quinten.bindIt.screens.MainMenu;
import com.quinten.bindIt.screens.SplashScreen;
import com.quinten.bindIt.screens.SubMenuScreen;

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
		setScreen(new SplashScreen(this));
	}
	
	public void splashScreenCompleted()
	{
		setScreen(new MainMenu(this));
	}
	
	public void playBonding()
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
}
