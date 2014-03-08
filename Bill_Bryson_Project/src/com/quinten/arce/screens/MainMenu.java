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

public class MainMenu implements Screen
{
	
	public MainMenu(BillBryson game)
	{
		this.game = game;
	}

	@Override
	public void render(float delta)
	{
		Gdx.gl.glClearColor(0.85F, 0.85F, 0.85F, 1F);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		stage.act(delta);
		
		spriteBatch.begin();
		
		blackBitmapFont.draw(spriteBatch, Reference.NAME, 
				(Gdx.graphics.getWidth() - (blackBitmapFont.getMultiLineBounds(Reference.NAME).width)) / 2,
				playButton.getY() + playButton.getHeight() + 200F);//TODO add a logo on the main menu
		blackBitmapFont.draw(spriteBatch, "Version: " + Reference.VERSION, 0, 
				blackBitmapFont.getMultiLineBounds("Version: " + Reference.VERSION).height + 1);
		stage.draw();
		
		spriteBatch.end();
	}

	@Override
	public void resize(int width, int height)
	{
		stage.setViewport(width, height, true);
		stage.clear();
		
		playButton.setX((width >>> 1) - playButton.getWidth() / 2);
		playButton.setY((height >>> 1) - playButton.getHeight() / 2);
		blackBitmapFont.setColor(0, 0, 0, 1);
		smallblackBitmapFont.setColor(0, 0, 0, 1);
		
		stage.addActor(playButton);
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
		smallblackBitmapFont = new BitmapFont(Gdx.files.internal(Reference.FONT_LOC + "/smallBlackFont.fnt"));
		
		playButtonStyle = new TextButtonStyle();
		playButtonStyle.up = skin.getDrawable("button_released");
		playButtonStyle.down = skin.getDrawable("button_pressed");
		playButtonStyle.font = blackBitmapFont;
		
		playButton = new TextButton("Play!", playButtonStyle);
		playButton.addListener(new InputListener()
		{
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button)
			{
				return true;
			}
			
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button)
			{
				Gdx.app.log(Reference.LOG_NAME, "play button pressed!");
				game.subMenuScreen();
			}
		});
		playButton.setWidth(400F);
		playButton.setHeight(100F);
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
		smallblackBitmapFont.dispose();
		stage.dispose();
	}
	
	private BillBryson game;
	private Stage stage;
	private BitmapFont whiteBitmapFont;
	private BitmapFont blackBitmapFont;
	private BitmapFont smallblackBitmapFont;//not working
	private TextureAtlas textureAtlas;
	private Skin skin;
	private SpriteBatch spriteBatch;
	
	private TextButtonStyle playButtonStyle;
	private TextButton playButton;
}
