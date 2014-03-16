package com.quinten.arce.screens;

import java.util.ArrayList;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.quinten.arce.game.DelayedEvent;
import com.quinten.arce.game.Occurable;

public class AddActors extends Occurable
{
	public AddActors(Stage stage, ArrayList<Actor> actors)
	{
		this.stage = stage;
		this.buttons = actors;
	}
	
	@Override
	public void occur()
	{
		if(buttons.isEmpty()) return;
		stage.addActor(buttons.get(0));
		buttons.remove(0);
		new DelayedEvent(SubMenuScreen.DISPLAY_DELAY, this).start();
	}
	
	Stage stage;
	ArrayList<Actor> buttons;
	
}
