package com.quinten.arce.game;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class PlayButton extends TextButton
{
	
	public PlayButton(String text, Skin skin)
	{
		super(text, skin);
	}
	
	public PlayButton(String text, TextButtonStyle style)
	{
		super(text, style);
	}
	
	public PlayButton(String text, Skin skin, String styleName)
	{
		super(text, skin, styleName);
	}
	
	public Completable source;
}
