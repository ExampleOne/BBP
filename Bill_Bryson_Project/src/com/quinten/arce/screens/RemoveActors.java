package com.quinten.arce.screens;

import java.util.ArrayList;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.quinten.arce.game.DelayedEvent;
import com.quinten.arce.game.Occurable;

public class RemoveActors extends Occurable
{
	public RemoveActors(Stage stage, ArrayList<Actor> buttons)
	{
		this.stage = stage;
		this.actors = buttons;
	}
	
	@Override
	public void occur()
	{
		if(actors.isEmpty()) return;
		stage.getRoot().removeActor(actors.get(0));
		actors.remove(0);
		new DelayedEvent(SubMenuScreen.DISPLAY_DELAY, this).start();
	}
	
	Stage stage;
	ArrayList<Actor> actors;
}
