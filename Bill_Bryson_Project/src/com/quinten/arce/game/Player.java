package com.quinten.arce.game;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;

public enum Player
{
	BLUE(Color.BLUE, "Blue Player"),
	RED(Color.RED, "Red Player"),
	GREEN(Color.GREEN, "Green Player"),
	MAGENTA(Color.MAGENTA, "Purple Player"),
	YELLOW(Color.YELLOW, "Yellow Player");
	
	Player(Color color, String name)
	{
		this.color = color;
		this.name = name;
	}
	
	public static final ArrayList<Player> getActivePlayers()
	{
		return activePlayers;
	}
	
	public void activate(String name, Difficulty difficulty)
	{
		this.name = name;
		this.difficulty = difficulty;
		activate();
	}
	
	public void activate(String name, Difficulty difficulty, Color color)
	{
		this.name = name;
		this.color = color;
		this.difficulty = difficulty;
		activate();
	}
	
	private synchronized void activate()
	{
		active = true;
	}
	
	public synchronized void deactivate()
	{
		active = false;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void addScore(int increment)
	{
		score += increment;
	}
	
	public void setScore(int score)
	{
		this.score = score;
	}
	
	public int getScore()
	{
		return score;
	}
	
	public Color getColor()
	{
		return color;
	}
	
	public void setColor(Color color)
	{
		this.color = color;
	}
	
	public void setDifficulty(Difficulty difficulty)
	{
		this.difficulty = difficulty;
	}
	
	public Difficulty getDifficulty()
	{
		return difficulty;
	}
	
	public boolean isActive()
	{
		return active;
	}
	
	private int score = 0;
	private String name;
	private Color color = Color.BLACK;
	private Difficulty difficulty;
	private boolean active;
	
	private static ArrayList<Player> activePlayers = new ArrayList<Player>();
}
