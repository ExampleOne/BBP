package com.quinten.arce.screens;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox.CheckBoxStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.utils.Array;
import com.quinten.arce.BillBryson;
import com.quinten.arce.Reference;
import com.quinten.arce.game.Completable;
import com.quinten.arce.game.DelayedEvent;
import com.quinten.arce.game.Difficulty;
import com.quinten.arce.game.PlayButton;
import com.quinten.arce.game.Player;



public class SubMenuScreen implements Screen, Completable
{
	public static final float BUTTON_WIDTH = 160F;
	public static final float BUTTON_HEIGHT = 40F;
	public static final float VERTICAL_COMPONENT_COUNT = 18;//Must be float for calculations!!
	public static final float HORIZONTAL_COMPONENT_COUNT = Reference.MAX_PLAYERS * 2 + 1;
	public static final float BUTTON_X_SPACING_PROPORTION = 1/HORIZONTAL_COMPONENT_COUNT;
	
	public static final int DISPLAY_DELAY = 100;
	
	public static final String PLAY_PROMPT = "Play!";	
	public static final String SELECTION_PROMPT = "Select your difficulties/players:";
	
	private static final float TITLE_HEIGHT = 17;
	private static final float PLAYER_BUTTON_HEIGHT = 15;
	private static final float NAME_ENTRY_HEIGHT = 13;
	private static final float EASY_BUTTON_HEIGHT = 11;
	private static final float MEDIUM_BUTTON_HEIGHT = 9;
	private static final float HARD_BUTTON_HEIGHT = 7;
	private static final float EXTREME_BUTTON_HEIGHT = 5;
	private static final float PLAY_BUTTON_HEIGHT = 2;
	
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
		stage.draw();
		spriteBatch.end();
	}
	
	@Override
	public void resize(int width, int height)
	{
		stage.setViewport(width, height, true);
		
		for(byte i = 0, length = (byte) easyButtons.length; i < length; i++)
		{
			playerButtons[i].setX(width * ((2 * i + 1) * BUTTON_X_SPACING_PROPORTION));
			playerButtons[i].setY(PLAYER_BUTTON_HEIGHT/VERTICAL_COMPONENT_COUNT * height);
			nameEntries[i].setX(width * ((2 * i + 1) * BUTTON_X_SPACING_PROPORTION));
			nameEntries[i].setY(NAME_ENTRY_HEIGHT/VERTICAL_COMPONENT_COUNT * height);
			easyButtons[i].setX(width * ((2 * i + 1) * BUTTON_X_SPACING_PROPORTION));
			easyButtons[i].setY(EASY_BUTTON_HEIGHT/VERTICAL_COMPONENT_COUNT * height);
			mediumButtons[i].setX(width * ((2 * i + 1) * BUTTON_X_SPACING_PROPORTION));
			mediumButtons[i].setY(MEDIUM_BUTTON_HEIGHT/VERTICAL_COMPONENT_COUNT * height);
			hardButtons[i].setX(width * ((2 * i + 1) * BUTTON_X_SPACING_PROPORTION));
			hardButtons[i].setY(HARD_BUTTON_HEIGHT/VERTICAL_COMPONENT_COUNT * height);
			extremeButtons[i].setX(width * ((2 * i + 1) * BUTTON_X_SPACING_PROPORTION));
			extremeButtons[i].setY(EXTREME_BUTTON_HEIGHT/VERTICAL_COMPONENT_COUNT * height);
		}
		
		playButton.setX((width - playButton.getWidth()) / 2);
		playButton.setY(PLAY_BUTTON_HEIGHT/VERTICAL_COMPONENT_COUNT * height);
		
		title.setX((width >>> 1) - title.getWidth() / 2);
		title.setY(TITLE_HEIGHT/VERTICAL_COMPONENT_COUNT * height);//TODO turn into logo
		version.setX(0);
		version.setY(0);		
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
		invertedBitmapFont = new BitmapFont(Gdx.files.internal(Reference.FONT_LOC + "/selectedFont.fnt"));
		
		smallBitmapFont.setColor(0, 0, 0, 1);
		blackBitmapFont.setColor(0, 0, 0, 1);
		
		difficulties = new ButtonGroup[Reference.MAX_PLAYERS + 1];
		buttonStyle = new CheckBoxStyle();
		buttonStyle.up = skin.getDrawable("button_released");
		buttonStyle.down = skin.getDrawable("button_pressed");
		buttonStyle.font = smallBitmapFont;
		buttonStyle.font.setColor(0, 0, 0, 1);
		buttonStyle.checkboxOff = skin.getDrawable("checkBox_off");
		buttonStyle.checkboxOn = skin.getDrawable("checkBox_on");
		
		playerButtons = new PlayerButton[Reference.MAX_PLAYERS];
		nameEntries = new NameField[Reference.MAX_PLAYERS];
		easyButtons = new CheckBox[Reference.MAX_PLAYERS];
		mediumButtons = new CheckBox[Reference.MAX_PLAYERS];
		hardButtons = new CheckBox[Reference.MAX_PLAYERS];
		extremeButtons = new CheckBox[Reference.MAX_PLAYERS];
		
		{
			TextButtonStyle style = new TextButtonStyle(buttonStyle);
			style.font = blackBitmapFont;
			style.font.setColor(0, 0, 0, 1);
			playButton = new PlayButton(PLAY_PROMPT, style);
			playButton.setWidth(BUTTON_WIDTH * 2);
			playButton.setHeight(BUTTON_HEIGHT * 2);
			playButton.setName(PLAY_PROMPT);
			playButton.addListener(listener);
			playButton.source = this;
			stage.addActor(playButton);
		}
		
		{
			Player[] players = Player.values();
			TextFieldStyle textStyle = new TextFieldStyle();
			textStyle.font = smallBitmapFont;
			textStyle.background = skin.getDrawable("textField_background");
			textStyle.cursor = skin.getDrawable("textField_cursor");
			textStyle.fontColor = new Color(0, 0, 0, 1);
			
			for(byte i = 0, length = (byte) easyButtons.length; i < length; i++)
			{
				difficulties[i] = new ButtonGroup();
				difficulties[i].setMaxCheckCount(1);
				difficulties[i].setMinCheckCount(0);
				
				playerButtons[i] = new PlayerButton(players[i].getName(), buttonStyle, difficulties[i], players[i]);
				nameEntries[i] = new NameField(players[i].getName(), textStyle, playerButtons[i]);
				easyButtons[i] = new CheckBox(Difficulty.EASY.getName() , buttonStyle);
				mediumButtons[i] = new CheckBox(Difficulty.MEDIUM.getName(), buttonStyle);
				hardButtons[i] = new CheckBox(Difficulty.HARD.getName(), buttonStyle);
				extremeButtons[i] = new CheckBox(Difficulty.EXTREME.getName(), buttonStyle);
				
				if(nameEntries[i].isDisabled()) nameEntries[i].setDisabled(false);
				
				playerButtons[i].setWidth(BUTTON_WIDTH);
				playerButtons[i].setHeight(BUTTON_HEIGHT);
				nameEntries[i].setWidth(BUTTON_WIDTH);
				nameEntries[i].setHeight(BUTTON_HEIGHT);
				easyButtons[i].setWidth(BUTTON_WIDTH);
				easyButtons[i].setHeight(BUTTON_HEIGHT);
				mediumButtons[i].setWidth(BUTTON_WIDTH);
				mediumButtons[i].setHeight(BUTTON_HEIGHT);
				hardButtons[i].setWidth(BUTTON_WIDTH);
				hardButtons[i].setHeight(BUTTON_HEIGHT);
				extremeButtons[i].setWidth(BUTTON_WIDTH);
				extremeButtons[i].setHeight(BUTTON_HEIGHT);
				
				playerButtons[i].setName(players[i].getName());
				nameEntries[i].setName(players[i].getName());
				easyButtons[i].setName(Difficulty.EASY.getName());
				mediumButtons[i].setName(Difficulty.MEDIUM.getName());
				hardButtons[i].setName(Difficulty.HARD.getName());
				extremeButtons[i].setName(Difficulty.EXTREME.getName());
				
				playerButtons[i].addListener(listener);
				nameEntries[i].addListener(listener);
				easyButtons[i].addListener(listener);
				mediumButtons[i].addListener(listener);
				hardButtons[i].addListener(listener);
				extremeButtons[i].addListener(listener);
				
				playerButtons[i].setColor(players[i].getColor());
				nameEntries[i].setColor(players[i].getColor());
				easyButtons[i].setColor(players[i].getColor());
				mediumButtons[i].setColor(players[i].getColor());
				hardButtons[i].setColor(players[i].getColor());
				extremeButtons[i].setColor(players[i].getColor());
				
				playerButtons[i].setNameEntry(nameEntries[i]);
				difficulties[i].add(playerButtons[i], easyButtons[i], mediumButtons[i], hardButtons[i], extremeButtons[i]);
			}
			buttonStyle.font.setColor(0, 0, 0, 1);
			
			LabelStyle titleStyle = new LabelStyle();
			titleStyle.font = blackBitmapFont;
			title = new Label(SELECTION_PROMPT, titleStyle);
			LabelStyle versionStyle = new LabelStyle();
			versionStyle.font = smallBitmapFont;
			version = new Label(Reference.VERSION, versionStyle);
		}
		
		for(byte i = 0, length = (byte) easyButtons.length; i < length; i++)
		{
			stage.addActor(playerButtons[i]);
		}
		stage.addActor(title);
		stage.addActor(version);
	}
	
	public void completed()
	{
		Array<Button> array;
		PlayerButton button;
		for(Player i : Player.values())
		{
			if(i.isActive())
			{
				button = findButtonOfPlayer(i);
				array = button.getButtonGroup().getAllChecked();
				if(array.get(0) == button)
				{
					i.activate(button.getNameEntry().getText(), Difficulty.deserialise(array.get(1).getName()));
				} else
				{
					i.activate(button.getNameEntry().getText(), Difficulty.valueOf(array.get(0).getName()));
				}
			}
		}
		Gdx.app.log(Reference.LOG_NAME, "Submenu completed!");
		game.startGame();
	}
	
	public PlayerButton findButtonOfPlayer(Player player)
	{
		for(PlayerButton i : playerButtons)
		{
			if(i.getPlayer() == player) return i;
		}
		throw new NoSuchElementException("No button for player " + player);
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
		invertedBitmapFont.dispose();
		stage.dispose();
	}
	
	private BillBryson game;
	private Stage stage;
	private BitmapFont whiteBitmapFont;
	private BitmapFont blackBitmapFont;
	private BitmapFont smallBitmapFont;
	private BitmapFont invertedBitmapFont;
	private TextureAtlas textureAtlas;
	private Skin skin;
	private SpriteBatch spriteBatch;
	
	private CheckBoxStyle buttonStyle;
	private PlayButton playButton;
	private PlayerButton[] playerButtons;
	private NameField[] nameEntries;
	private CheckBox[] easyButtons;
	private CheckBox[] mediumButtons;
	private CheckBox[] hardButtons;
	private CheckBox[] extremeButtons;
	private ButtonGroup[] difficulties;
	private Label title;
	private Label version;
	
	private final InputListener listener = new InputListener()
	{
		
		@Override
		public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) 
		{
	 		return true;
	 	}
		
		@Override
		public boolean keyUp(InputEvent event, int keycode)
		{
			Actor actor = event.getListenerActor();
			if(actor instanceof NameField)
	 		{
	 			((NameField) actor).getPlayerButton().setText(((NameField) actor).getText());;
	 		}
			return false;
		}
		
	 	@Override
	 	public void touchUp (InputEvent event, float x, float y, int pointer, int button) 
	 	{
	 		Actor actor = event.getListenerActor();
	 		if(actor instanceof PlayerButton) playerButtonPressed((PlayerButton) event.getListenerActor(), event);
	 		else if(actor == playButton)
	 		{
	 			playButton.source.completed();
	 			return;
	 		}
	 		else if(actor instanceof NameField)
	 		{
	 			((NameField) actor).getPlayerButton().setText(((NameField) actor).getText());;
	 			return;
	 		}
	 		Gdx.app.log(Reference.LOG_NAME, event.getListenerActor().getName() + " button pressed!");
	 		
	 	}

		private void playerButtonPressed(PlayerButton button, InputEvent event)
		{
			Stage stage = event.getStage();
			ArrayList<Actor> actors = toArrayList(button.getButtonGroup().getButtons(), button.getNameEntry());
			actors.remove(button);
			
			if(!button.getPlayer().isActive())
			{
				new DelayedEvent(DISPLAY_DELAY, new AddActors(stage, actors)).start();
				button.getButtonGroup().setMaxCheckCount(2);
				button.getButtonGroup().setMinCheckCount(2);
				button.getButtonGroup().getButtons().get(1).setChecked(true);
				button.getPlayer().activate(null, null);
			} else
			{
				new DelayedEvent(DISPLAY_DELAY, new RemoveActors(stage, actors)).start();
				button.getButtonGroup().uncheckAll();
				button.getButtonGroup().setMaxCheckCount(1);
				button.getButtonGroup().setMinCheckCount(0);
				button.getPlayer().deactivate();
			}
		}
		
		private ArrayList<Actor> toArrayList(Array<Button> array, Actor ... a) 
		{
			ArrayList<Actor> result = new ArrayList<Actor>(array.size);
			for(Actor i : a) result.add(i);
			for(Button i : array) result.add(i);
			return result;
		}
	};
}
