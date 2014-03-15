package com.quinten.arce.screens;

import java.util.ArrayList;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.quinten.arce.game.DelayedEvent;
import com.quinten.arce.game.Occurable;

public class RemoveActors extends Occurable
{
	public RemoveActors(Stage stage, ArrayList<Button> buttons)
	{
		this.stage = stage;
		this.buttons = buttons;
	}
	
	@Override
	public void occur()
	{
		if(buttons.isEmpty()) return;
		stage.getRoot().removeActor(buttons.get(0));
		buttons.remove(0);
		new DelayedEvent(SubMenuScreen.DISPLAY_DELAY, this).start();
	}
	
	Stage stage;
	ArrayList<Button> buttons;
}
