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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
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
		stage.draw();		
		spriteBatch.end();
	}

	@Override
	public void resize(int width, int height)
	{
		stage.setViewport(width, height, true);
		
		playButton.setX((width >>> 1) - playButton.getWidth() / 2);
		playButton.setY((height >>> 1) - playButton.getHeight() / 2);
		title.setX((width >>> 1) - title.getWidth() / 2);
		title.setY(playButton.getY() + (height - playButton.getY() + playButton.getHeight())/2);//TODO turn into logo
		version.setX(0);
		version.setY(0);
		
		blackBitmapFont.setColor(0, 0, 0, 1);
		smallBitmapFont.setColor(0, 0, 0, 1);
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
		smallBitmapFont = new BitmapFont(Gdx.files.internal(Reference.FONT_LOC + "/smallBlackFont.fnt"));
		
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
		
		LabelStyle titleStyle = new LabelStyle();
		titleStyle.font = blackBitmapFont;
		title = new Label(Reference.NAME, titleStyle);
		LabelStyle versionStyle = new LabelStyle();
		versionStyle.font = smallBitmapFont;
		version = new Label(Reference.VERSION, versionStyle);
		
		stage.addActor(playButton);
		stage.addActor(title);
		stage.addActor(version);
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
		smallBitmapFont.dispose();
		stage.dispose();
	}
	
	private BillBryson game;
	private Stage stage;
	private BitmapFont whiteBitmapFont;
	private BitmapFont blackBitmapFont;
	private BitmapFont smallBitmapFont;
	private TextureAtlas textureAtlas;
	private Skin skin;
	private SpriteBatch spriteBatch;
	
	private TextButtonStyle playButtonStyle;
	private TextButton playButton;
	private Label title;
	private Label version;
}
