package com.quinten.arce.screens;

import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.quinten.arce.game.Player;

public class PlayerButton extends CheckBox
{
	public PlayerButton(String text, Skin skin, ButtonGroup buttonGroup, Player player)
	{
		super(text, skin);
		initialise(buttonGroup, player);
	}
	
	public PlayerButton(String text, CheckBoxStyle style, ButtonGroup buttonGroup, Player player)
	{
		super(text, style);
		initialise(buttonGroup, player);
	}
	
	public PlayerButton(String text, Skin skin, String styleName, ButtonGroup buttonGroup, Player player)
	{
		super(text, skin, styleName);
		initialise(buttonGroup, player);
	}
	
	private void initialise(ButtonGroup buttonGroup, Player player)
	{
		this.buttonGroup = buttonGroup;
		this.player = player;
	}
	
	public ButtonGroup getButtonGroup()
	{
		return buttonGroup;
	}
	
	public Player getPlayer()
	{
		return player;
	}

	public void setNameEntry(TextField nameEntry)
	{
		this.nameEntry = nameEntry;
	}
	
	public TextField getNameEntry()
	{
		return nameEntry;
	}

	private ButtonGroup buttonGroup;
	private TextField nameEntry;
	private Player player;
}
