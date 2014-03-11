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
import com.quinten.arce.game.Difficulty;
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
		assignImages();
		
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
	
	private void assignImages()
	{
		printArray(getSortedBytes());		
	}
	
	private byte[][] getSortedBytes()
	{
		Random random = new Random();
		byte[][] result = new byte[buttons.length][buttons[0].length];
		final byte difference = (byte) (difficulty.getMaxDifficulty() - difficulty.getMinDifficulty() + 1);
		
		for(int i = 0; i < buttons.length; i++)
		{
			for(int j = 0; j < buttons[i].length; j++)
			{
				result[i][j] = (byte) (difficulty.getMinDifficulty() + random.nextInt(difference));
			}
			result[i] = sortBytes(result[i]);
		}
		return result;
	}
	
	private static byte[] sortBytes(byte[] array)
	{
		int index = -1;
		for(int a = 0; a < array.length; a++)
		{
			index = findIndexofSmallestAfter(array, a); 
			if(index != a)
			{
				array = swap(array, index, a);
			}
		}
		return array;
	}
	
	private static int findIndexofSmallestAfter(byte[] array, int startIndex)
	{
		int result = startIndex;
		for(int i = startIndex; i < array.length; i++)
		{
			if(array[i] < array[result]) result = i;
		}
		return result;
	}
	
	private static byte[] swap(byte[] array, int firstIndex, int secondIndex)
	{
		byte temp = array[firstIndex];
		array[firstIndex] = array[secondIndex];
		array[secondIndex] = temp;
		return array;
	}
	
	public static void printArray(byte[][] array)
	{
		for(byte[] i : array)
		{
			for(byte j : i)
			{
				System.out.print("[" + j + "]");
			}
			System.out.println();
		}
	}
	
	private BillBryson game;
	private Difficulty difficulty;
	private int score;
	private Stage stage;
	private BitmapFont whiteBitmapFont;
	private BitmapFont blackBitmapFont;
	private TextureAtlas textureAtlas;
	private Skin skin;
	private SpriteBatch spriteBatch;
	
	private TextButtonStyle buttonStyle;
	private QuestionButton[][] buttons = new QuestionButton[3][5];
}
