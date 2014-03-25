package com.quinten.arce;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class Reference 
{
	/**
	 * The version of the game.
	 */
	public static final String VERSION = "Pre-Alpha 0.0.0";
	public static final String NAME = "NAME";
	public static final String LOG_NAME = "NAME";
	public static final String LOG_TIME = "Time";
	
	public static final int WIDTH = 1080;
	public static final int HEIGHT = 720;
	
	public static final boolean USE_GL_2_0 = false;

	public static final String QUESTION_UNAVAILABLE = "There was no question specified.";
	public static final String ANSWERS_UNAVAILABLE = "There was/were no answer(s) specified.";
	public static final FileHandle QUESTIONS_FILEHANDLE = Gdx.files.internal("questions/questions.txt");
	
	public static final String NO_PLAYER_NAME = "No name specified";
	public static final byte MAX_PLAYERS = 5;
	
	public static final String DATA_LOC = "";
	public static final String PICTURE_LOC = DATA_LOC + "images";
	public static final String TEXTURE_LOC = DATA_LOC + "ninePatches";
	public static final String FONT_LOC = DATA_LOC + "fonts";
	public static final String QUESTION_IMAGE_LOC = "questions/question images";
	public static final String ANSWER_IMAGE_LOC = "questions/answer images";
	public static final String CORRECT_ANSWER_SUFFIX = "_correct";
	public static final String WRONG_ANSWER_SUFFIX = "_wrong";
	public static final String BUTTON_PRESSED_SUFFIX = "button_pressed";
	public static final String BUTTON_RELEASED_SUFFIX = "button_released";
	
	public static final String PNG_EXTENSION = ".png";
	
	public static final int SCORE_INTERVAL = 100;
	public static final byte NUM_SCORES = 5;
	
	public static final float SPLASH_SCREEN_DELAY = 3F;
	
	private Reference()
	{}
}
