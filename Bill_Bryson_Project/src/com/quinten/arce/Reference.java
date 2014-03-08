package com.quinten.arce;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class Reference 
{
	/**
	 * The version of the game.
	 */
	public static final String VERSION = "Pre-Alpha 0.0.0";
	public static final String NAME = "Arce!";
	public static final String LOG_NAME = "Project";
	
	public static final int WIDTH = 1080;
	public static final int HEIGHT = 720;
	
	public static final boolean USE_GL_2_0 = true;
	
	public static final String DATA_LOC = "";
	public static final String PICTURE_LOC = DATA_LOC + "images";
	public static final String TEXTURE_LOC = DATA_LOC + "ninePatches";
	public static final String FONT_LOC = DATA_LOC + "fonts";
	
	public static final String QUESTION_UNAVAILABLE = "There was no question specified.";
	public static final String ANSWERS_UNAVAILANLE = "There was/were no answer(s) specified.";
	public static final FileHandle QUESTIONS_FILEHANDLE = Gdx.files.internal("questions/questions.txt");	
	
	public static final float SPLASH_SCREEN_DELAY = 3F;
	
	private Reference()
	{}

}
