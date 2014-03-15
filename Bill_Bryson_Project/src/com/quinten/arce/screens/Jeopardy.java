package com.quinten.arce.screens;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.quinten.arce.BillBryson;
import com.quinten.arce.Reference;
import com.quinten.arce.game.Catagory;
import com.quinten.arce.game.Difficulty;
import com.quinten.arce.game.Question;
import com.quinten.arce.game.QuestionButton;

public class Jeopardy implements Screen
{
	
	public Jeopardy(BillBryson game, Difficulty difficulty)
	{
		this.game = game;
		this.difficulty = difficulty;
	}
	
	@Override
	public void render(float delta)
	{
		Gdx.gl.glClearColor(0.85F, 0.85F, 0.85F, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	}
	
	@Override
	public void resize(int width, int height)
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void show()
	{
		assignQuestions();
		
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
		stage.dispose();
		whiteBitmapFont.dispose();
		blackBitmapFont.dispose();
		textureAtlas.dispose();
		skin.dispose();
	}
	
	private void assignQuestions()
	{
		Question[][] sortedQuestions = getSortedQuestions();
		
	}
	
	private Question[][] getSortedQuestions()
	{
		Question[][] result = new Question[buttons.length][buttons[0].length];
		Random random = new Random();
		final byte difference = (byte) ((difficulty.getMaxDifficulty() - difficulty.getMinDifficulty())/Reference.NUM_SCORES);
		byte state;
		byte catagoryId;
		Question candidate = null;
		int indexCandidate = 0;
		
		for(Catagory catagory : Catagory.values())
		{
			state = difficulty.getMinDifficulty();
			catagoryId = catagory.getId();
			for(int i = 0, length = buttons[catagoryId].length; i < length; i++)
			{
				for(byte a = 0; a < 10 && 
						(contains(result[catagoryId], candidate) || candidate == null); a++)
				{
					indexCandidate = Difficulty.findFirstOfDifficulty(difficulty, (byte) (state + random.nextInt(difference)), catagory);
					if(indexCandidate == -1) continue;
					candidate = difficulty.getQuestions().get(indexCandidate);
				}
				state += difference;
				result[catagoryId][i] = candidate;
			}
		}
		return result;
	}
	
	public static <T> boolean contains(T[] array, T object)
	{
		for(T i : array)
		{
			if(i == object) return true;
		}
		return false;
	}
	
	private BillBryson game;
	private Difficulty difficulty;
	private Stage stage;
	private BitmapFont whiteBitmapFont;
	private BitmapFont blackBitmapFont;
	private TextureAtlas textureAtlas;
	private Skin skin;
	private SpriteBatch spriteBatch;
	
	private TextButtonStyle buttonStyle;
	private QuestionButton[][] buttons = new QuestionButton[Catagory.values().length][Reference.NUM_SCORES];
}
