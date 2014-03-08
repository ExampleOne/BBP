package com.quinten.arce.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.quinten.arce.BillBryson;
import com.quinten.arce.Reference;
import com.quinten.arce.game.Difficulty;

public class SubMenuScreen implements Screen
{
	public static final float BUTTON_WIDTH = 250F;
	public static final float BUTTON_HEIGHT = 75F;
	
	public SubMenuScreen(BillBryson game)
	{
		this.game = game;
	}
	
	@Override
	public void render(float delta)
	{
		Gdx.gl.glClearColor(0.85F, 0.85F, 0.85F, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		stage.act(delta);
		
		spriteBatch.begin();
		
		blackBitmapFont.draw(spriteBatch, Reference.NAME, 
				(Gdx.graphics.getWidth() - (blackBitmapFont.getMultiLineBounds(Reference.NAME).width)) / 2,
				easyButton.getY() + easyButton.getHeight() + 200F);//TODO add a logo on the main menu
		blackBitmapFont.draw(spriteBatch, "Version: " + Reference.VERSION, 0, 
				blackBitmapFont.getMultiLineBounds("Version: " + Reference.VERSION).height + 1);
		stage.draw();
		
		spriteBatch.end();
	}
	
	@Override
	public void resize(int width, int height)
	{
		stage.setViewport(width, height, true);
	}
	
	@Override
	public void show()
	{
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		
		spriteBatch = new SpriteBatch();
		textureAtlas = new TextureAtlas(Reference.TEXTURE_LOC + "/button/button.pack");
		skin = new Skin();
		skin.addRegions(textureAtlas);
		
		whiteBitmapFont = new BitmapFont(Gdx.files.internal(Reference.FONT_LOC + "/whiteFont.fnt"));
		blackBitmapFont = new BitmapFont(Gdx.files.internal(Reference.FONT_LOC + "/blackFont.fnt"));
		
		buttonStyle = new TextButtonStyle();
		buttonStyle.up = skin.getDrawable("button_released");
		buttonStyle.down = skin.getDrawable("button_pressed");
		buttonStyle.font = blackBitmapFont;
		
		easyButton = new TextButton(Difficulty.EASY.getName() , buttonStyle);
		mediumButton = new TextButton(Difficulty.MEDIUM.getName(), buttonStyle);
		hardButton = new TextButton(Difficulty.HARD.getName(), buttonStyle);
		extremeButton = new TextButton(Difficulty.EXTREME.getName(), buttonStyle);
		
		easyButton.setWidth(BUTTON_WIDTH);
		easyButton.setHeight(BUTTON_HEIGHT);
		mediumButton.setWidth(BUTTON_WIDTH);
		mediumButton.setHeight(BUTTON_HEIGHT);
		hardButton.setWidth(BUTTON_WIDTH);
		hardButton.setHeight(BUTTON_HEIGHT);
		extremeButton.setWidth(BUTTON_WIDTH);
		extremeButton.setHeight(BUTTON_HEIGHT);
		
		InputListener listener = new InputListener()
		{
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) 
			{
		 		return true;
		 	}
		 
		 	public void touchUp (InputEvent event, float x, float y, int pointer, int button) 
		 	{
		 		Gdx.app.log(Reference.LOG_NAME, event.getListenerActor().getName() + "button was pressed.");
		 	}
		};
		
		easyButton.addListener(listener);
		mediumButton.addListener(listener);
		hardButton.addListener(listener);
		extremeButton.addListener(listener);
	}
	
	@Override
	public void hide()
	{}
	
	@Override
	public void pause()
	{}
	
	@Override
	public void resume()
	{}
	
	@Override
	public void dispose()
	{
		spriteBatch.dispose();
		skin.dispose();
		textureAtlas.dispose();
		whiteBitmapFont.dispose();
		blackBitmapFont.dispose();
		stage.dispose();
	}
	
	private BillBryson game;
	private Stage stage;
	private BitmapFont whiteBitmapFont;
	private BitmapFont blackBitmapFont;
	private TextureAtlas textureAtlas;
	private Skin skin;
	private SpriteBatch spriteBatch;
	
	private TextButtonStyle buttonStyle;
	private TextButton easyButton;
	private TextButton mediumButton;
	private TextButton hardButton;
	private TextButton extremeButton;
}
