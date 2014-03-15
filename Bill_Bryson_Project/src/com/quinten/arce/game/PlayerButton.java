package com.quinten.arce.game;

import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

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

	private ButtonGroup buttonGroup;
	private Player player;
}
