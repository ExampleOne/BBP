package com.quinten.arce.tweenAccessors;

import aurelienribon.tweenengine.TweenAccessor;

import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * A sprite tween accessor that alters only the
 * alpha value.
 * @author quinten
 *
 */
public class SpriteTween implements TweenAccessor<Sprite>
{
	public static final int ALPHA = 1;

	public SpriteTween() 
	{}

	@Override
	public int getValues(Sprite target, int tweenType, float[] returnValues)
	{
		switch(tweenType)
		{
			case ALPHA:
			{
				returnValues[0] = target.getColor().a;
				return 1;
			}
			default:
			{
				return 0;
			}
		}
	}

	@Override
	public void setValues(Sprite target, int tweenType, float[] newValues)
	{
		switch(tweenType)
		{
			case ALPHA:
			{
				target.setColor(1, 1, 1, newValues[0]);
				break;
			}
		}
	}

}
