package com.quinten.arce.screens;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

public class NameField extends TextField
{
	
	public NameField(String text, Skin skin, PlayerButton playerButton)
	{
		super(text, skin);
		initialise(playerButton);
	}
	
	public NameField(String text, TextFieldStyle style, PlayerButton playerButton)
	{
		super(text, style);
		initialise(playerButton);
	}
	
	public NameField(String text, Skin skin, String styleName, PlayerButton playerButton)
	{
		super(text, skin, styleName);
		initialise(playerButton);
	}
	
	private void initialise(PlayerButton playerButton)
	{
		this.setPlayerButton(playerButton);
	}
	

	public void setPlayerButton(PlayerButton playerButton)
	{
		this.playerButton = playerButton;
	}
	
	public PlayerButton getPlayerButton()
	{
		return playerButton;
	}

	private PlayerButton playerButton;
}
