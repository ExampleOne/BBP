package com.quinten.arce.screens;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox.CheckBoxStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.Array;
import com.quinten.arce.BillBryson;
import com.quinten.arce.Reference;
import com.quinten.arce.game.DelayedEvent;
import com.quinten.arce.game.Difficulty;
import com.quinten.arce.game.Occurable;
import com.quinten.arce.game.Player;
import com.quinten.arce.game.PlayerButton;

public class SubMenuScreen implements Screen
{
	public static final float BUTTON_WIDTH = 160F;
	public static final float BUTTON_HEIGHT = 40F;
	public static final float VERTICAL_COMPONENT_COUNT = 16;//Must be float for calculations!!
	public static final float HORIZONTAL_COMPONENT_COUNT = Reference.MAX_PLAYERS * 2 + 1;
	public static final float BUTTON_X_SPACING_PROPORTION = 1/HORIZONTAL_COMPONENT_COUNT;
	
	public static final int DISPLAY_DELAY = 100;
	
	public static final String PLAY_PROMPT = "Play!";	
	public static final String SELECTION_PROMPT = "Select your difficulties/players:";
	
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
		
		smallBitmapFont.draw(spriteBatch, "Version: " + Reference.VERSION, 0, 
				smallBitmapFont.getMultiLineBounds("Version: " + Reference.VERSION).height + 1);
		blackBitmapFont.draw(spriteBatch, SELECTION_PROMPT, 
				(Gdx.graphics.getWidth() >>> 1) - (blackBitmapFont.getBounds(SELECTION_PROMPT).width/2),
				15/VERTICAL_COMPONENT_COUNT * Gdx.graphics.getHeight());//TODO add a logo on the main menu		
		stage.draw();		
		
		spriteBatch.end();
	}
	
	@Override
	public void resize(int width, int height)
	{
		stage.setViewport(width, height, true);
		
		for(byte i = 0, length = (byte) easyButtons.length; i < length; i++)
		{
			playerButtons[i].setX(Gdx.graphics.getWidth() * ((2 * i + 1) * BUTTON_X_SPACING_PROPORTION));
			playerButtons[i].setY(13/VERTICAL_COMPONENT_COUNT * Gdx.graphics.getHeight());
			easyButtons[i].setX(Gdx.graphics.getWidth() * ((2 * i + 1) * BUTTON_X_SPACING_PROPORTION));
			easyButtons[i].setY(11/VERTICAL_COMPONENT_COUNT * Gdx.graphics.getHeight());
			mediumButtons[i].setX(Gdx.graphics.getWidth() * ((2 * i + 1) * BUTTON_X_SPACING_PROPORTION));
			mediumButtons[i].setY(9/VERTICAL_COMPONENT_COUNT * Gdx.graphics.getHeight());
			hardButtons[i].setX(Gdx.graphics.getWidth() * ((2 * i + 1) * BUTTON_X_SPACING_PROPORTION));
			hardButtons[i].setY(7/VERTICAL_COMPONENT_COUNT * Gdx.graphics.getHeight());
			extremeButtons[i].setX(Gdx.graphics.getWidth() * ((2 * i + 1) * BUTTON_X_SPACING_PROPORTION));
			extremeButtons[i].setY(5/VERTICAL_COMPONENT_COUNT * Gdx.graphics.getHeight());
		}
		
		playButton.setX((Gdx.graphics.getWidth() - playButton.getWidth()) / 2);
		playButton.setY(2/VERTICAL_COMPONENT_COUNT * Gdx.graphics.getHeight());
		
		smallBitmapFont.setColor(0, 0, 0, 1);
		blackBitmapFont.setColor(0, 0, 0, 1);
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
		
		difficulties = new ButtonGroup[Reference.MAX_PLAYERS + 1];
		buttonStyle = new CheckBoxStyle();
		buttonStyle.up = skin.getDrawable("button_released");
		buttonStyle.down = skin.getDrawable("button_pressed");
		buttonStyle.font = smallBitmapFont;
		buttonStyle.checkboxOff = skin.getDrawable("checkBox_off");
		buttonStyle.checkboxOn = skin.getDrawable("checkBox_on");
		
		playerButtons = new PlayerButton[Reference.MAX_PLAYERS];
		easyButtons = new CheckBox[Reference.MAX_PLAYERS];
		mediumButtons = new CheckBox[Reference.MAX_PLAYERS];
		hardButtons = new CheckBox[Reference.MAX_PLAYERS];
		extremeButtons = new CheckBox[Reference.MAX_PLAYERS];
		
		{
			TextButtonStyle style = new TextButtonStyle(buttonStyle);
			style.font = blackBitmapFont;
			playButton = new TextButton(PLAY_PROMPT, style);
			playButton.setWidth(BUTTON_WIDTH * 2);
			playButton.setHeight(BUTTON_HEIGHT * 2);
			playButton.setName(PLAY_PROMPT);
			playButton.addListener(listener);
			stage.addActor(playButton);
		}
		
		{
			Player[] players = Player.values();
			for(byte i = 0, length = (byte) easyButtons.length; i < length; i++)
			{
				difficulties[i] = new ButtonGroup();
				difficulties[i].setMaxCheckCount(1);
				difficulties[i].setMinCheckCount(0);
				
				playerButtons[i] = new PlayerButton(players[i].getName(), buttonStyle, difficulties[i], players[i]);
				easyButtons[i] = new CheckBox(Difficulty.EASY.getName() , buttonStyle);
				mediumButtons[i] = new CheckBox(Difficulty.MEDIUM.getName(), buttonStyle);
				hardButtons[i] = new CheckBox(Difficulty.HARD.getName(), buttonStyle);
				extremeButtons[i] = new CheckBox(Difficulty.EXTREME.getName(), buttonStyle);
				
				playerButtons[i].setWidth(BUTTON_WIDTH);
				playerButtons[i].setHeight(BUTTON_HEIGHT);
				easyButtons[i].setWidth(BUTTON_WIDTH);
				easyButtons[i].setHeight(BUTTON_HEIGHT);
				mediumButtons[i].setWidth(BUTTON_WIDTH);
				mediumButtons[i].setHeight(BUTTON_HEIGHT);
				hardButtons[i].setWidth(BUTTON_WIDTH);
				hardButtons[i].setHeight(BUTTON_HEIGHT);
				extremeButtons[i].setWidth(BUTTON_WIDTH);
				extremeButtons[i].setHeight(BUTTON_HEIGHT);
				
				playerButtons[i].setName(players[i].getName());
				easyButtons[i].setName(Difficulty.EASY.getName());
				mediumButtons[i].setName(Difficulty.MEDIUM.getName());
				hardButtons[i].setName(Difficulty.HARD.getName());
				extremeButtons[i].setName(Difficulty.EXTREME.getName());
				
				playerButtons[i].addListener(listener);
				easyButtons[i].addListener(listener);
				mediumButtons[i].addListener(listener);
				hardButtons[i].addListener(listener);
				extremeButtons[i].addListener(listener);
				
				difficulties[i].add(playerButtons[i], easyButtons[i], mediumButtons[i], hardButtons[i], extremeButtons[i]);
			}
		}
		
		for(byte i = 0, length = (byte) easyButtons.length; i < length; i++)
		{
			stage.addActor(playerButtons[i]);
		}
	}
	
	private void screenCompleted(Difficulty difficulty)
	{
		Gdx.app.log(Reference.LOG_NAME, difficulty + " button was pressed.");
		game.startGame(difficulty);
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
	
	private CheckBoxStyle buttonStyle;
	private TextButton playButton;
	private PlayerButton[] playerButtons;
	private CheckBox[] easyButtons;
	private CheckBox[] mediumButtons;
	private CheckBox[] hardButtons;
	private CheckBox[] extremeButtons;
	private ButtonGroup[] difficulties;
	
	private static final InputListener listener = new InputListener()
	{
		
		@Override
		public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) 
		{
	 		return true;
	 	}
		
	 	@Override
	 	public void touchUp (InputEvent event, float x, float y, int pointer, int button) 
	 	{
	 		Gdx.app.log(Reference.LOG_NAME, event.getListenerActor().getName() + " button pressed!");
	 		if(event.getListenerActor() instanceof PlayerButton) playerButtonPressed((PlayerButton) event.getListenerActor(), event);
	 	}

		private void playerButtonPressed(PlayerButton button, InputEvent event)
		{
			Stage stage = event.getStage();
			ArrayList<Button> buttons = toArrayList(button.getButtonGroup().getButtons());
			buttons.remove(button);
			
			if(!button.getPlayer().isActive())
			{
				new DelayedEvent(DISPLAY_DELAY, new AddActors(stage, buttons)).start();
				button.getButtonGroup().setMaxCheckCount(2);
				button.getButtonGroup().setMinCheckCount(2);
				button.getPlayer().activate(null, null);
			} else
			{
				new DelayedEvent(DISPLAY_DELAY, new RemoveActors(stage, buttons)).start();
				button.getButtonGroup().uncheckAll();
				button.getButtonGroup().setMaxCheckCount(1);
				button.getButtonGroup().setMinCheckCount(0);
				button.getPlayer().deactivate();
			}
		}
		
		private <T> ArrayList<T> toArrayList(Array<T> array)
		{
			ArrayList<T> result = new ArrayList<T>(array.size);
			for(T i : array)
			{
				result.add(i);
			}
			return result;
		}
	};
}
