package com.quinten.arce.screens;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.quinten.arce.BillBryson;
import com.quinten.arce.Reference;
import com.quinten.arce.tweenAccessors.SpriteTween;

public class SplashScreen implements Screen 
{
	
	public SplashScreen(BillBryson game) 
	{
		this.game = game;
	}

	@Override
	public void render(float delta) 
	{
		Gdx.gl.glClearColor(0.8828125F, 0.8828125F, 0.8828125F, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		spriteBatch.begin();
		creditsSprite.draw(spriteBatch);
		awardNameSprite.draw(spriteBatch);
		titleSprite.draw(spriteBatch);
		spriteBatch.end();
		
		tweenManager.update(delta);
	}

	@Override
	public void resize(int width, int height)
	{
		
	}

	@Override
	public void show() 
	{
		titleTexture = new Texture(Reference.PICTURE_LOC + "/splashScreenTitle.png");
		titleTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		creditsTexture = new Texture(Reference.PICTURE_LOC + "/splashScreenCredits.png");
		creditsTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		awardNameTexture = new Texture(Reference.PICTURE_LOC + "/splashScreenAwardName.png");
		awardNameTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		creditsSprite = new Sprite(creditsTexture);
		creditsSprite.setOrigin(0F, 0F);
		creditsSprite.setPosition(0F, 0F);
		creditsSprite.setColor(1F, 1F, 1F, 1F);//the starting colour
		
		awardNameSprite = new Sprite(awardNameTexture);
		awardNameSprite.setOrigin(0F, 0F);
		awardNameSprite.setPosition(Gdx.graphics.getWidth() - awardNameTexture.getWidth(), 0F);
		awardNameSprite.setColor(1F, 1F, 1F, 1F);
		
		titleSprite = new Sprite(titleTexture);
		titleSprite.setOrigin(0F, 0F);
		titleSprite.setPosition((Gdx.graphics.getWidth() - titleTexture.getWidth()) / 2, (Gdx.graphics.getHeight() - titleTexture.getHeight()) / 2);
		titleSprite.setColor(1F, 1F, 1F, 1F);
		
		spriteBatch = new SpriteBatch();
		
		Gdx.input.setInputProcessor(new InputProcessor() 
		{	@Override
			public boolean keyDown(int keycode)
			{return false;}

			@Override
			public boolean keyUp(int keycode)
			{return false;}

			@Override
			public boolean keyTyped(char character)
			{return false;}

			@Override
			public boolean touchDown(int screenX, int screenY, int pointer,
					int button)
			{
				screenCompleted();
				return true;
			}

			@Override
			public boolean touchUp(int screenX, int screenY, int pointer,
					int button)
			{return false;}

			@Override
			public boolean touchDragged(int screenX, int screenY, int pointer)
			{return false;}

			@Override
			public boolean mouseMoved(int screenX, int screenY)
			{return false;}

			@Override
			public boolean scrolled(int amount)
			{return false;}
		});
		
		Tween.registerAccessor(Sprite.class, new SpriteTween());
		
		tweenManager = new TweenManager();
		
		TweenCallback tweenCallback = new TweenCallback()
		{

			@Override
			public void onEvent(int type, BaseTween<?> source)
			{
				tweenManager.killAll();
				screenCompleted();
			}
			
		};
		
		Tween.to(creditsSprite, SpriteTween.ALPHA, Reference.SPLASH_SCREEN_DELAY).target(0F).ease(TweenEquations.easeInQuad)
			.setCallback(tweenCallback).setCallbackTriggers(TweenCallback.COMPLETE).start(tweenManager);
		Tween.to(awardNameSprite, SpriteTween.ALPHA, Reference.SPLASH_SCREEN_DELAY).target(0F).ease(TweenEquations.easeInQuad)
		.setCallback(tweenCallback).setCallbackTriggers(TweenCallback.COMPLETE).start(tweenManager);
		Tween.to(titleSprite, SpriteTween.ALPHA, Reference.SPLASH_SCREEN_DELAY).target(0F).ease(TweenEquations.easeInQuad)
		.setCallback(tweenCallback).setCallbackTriggers(TweenCallback.COMPLETE).start(tweenManager);
	}
	
	private final void screenCompleted()
	{
		Gdx.app.log(Reference.LOG_NAME, "Splash Screen Completed.");
		game.splashScreenCompleted();
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
		titleTexture.dispose();
	}
	
	private Texture titleTexture;
	private Texture creditsTexture;
	private Texture awardNameTexture;
	private Sprite creditsSprite;
	private Sprite awardNameSprite;
	private Sprite titleSprite;
	private SpriteBatch spriteBatch;
	private BillBryson game;
	private TweenManager tweenManager;
}
