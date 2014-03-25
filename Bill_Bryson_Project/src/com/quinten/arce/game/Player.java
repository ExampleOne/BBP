package com.quinten.arce.game;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import com.badlogic.gdx.graphics.Color;
import com.quinten.arce.Reference;

public enum Player
{
	BLUE(new Color(Color.BLUE.r, Color.BLUE.g, Color.BLUE.b, .5F), "Blue Player"),
	RED(new Color(Color.RED.r, Color.RED.g, Color.RED.b, .5F), "Red Player"),
	GREEN(new Color(Color.GREEN.r, Color.GREEN.g, Color.GREEN.b, .5F), "Green Player"),
	MAGENTA(new Color(Color.MAGENTA.r, Color.MAGENTA.g, Color.MAGENTA.b, .5F), "Purple Player"),
	YELLOW(new Color(Color.YELLOW.r, Color.YELLOW.g, Color.YELLOW.b, .5F), "Yellow Player");
	
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
		if(name == "" || name == null) name = Reference.NO_PLAYER_NAME;
		this.difficulty = difficulty;
		activate();
	}
	
	public void activate(String name, Difficulty difficulty, Color color)
	{
		this.name = name;
		if(name == "" || name == null) name = Reference.NO_PLAYER_NAME;
		this.color = color;
		this.difficulty = difficulty;
		activate();
	}
	
	private synchronized void activate()
	{
		if(active.compareAndSet(false, true)) activePlayers.add(this);
	}
	
	public synchronized void deactivate()
	{
		if(active.compareAndSet(true, false)) activePlayers.remove(this);
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
		return active.get();
	}
	
	public void setQuestions(Question[][] questions)
	{
		this.questions = questions;
	}
	
	public Question[][] getQuestions()
	{
		return questions;
	}
	
	@Override
	public String toString()
	{
		return name;
	}

	private int score = 0;
	private String name;
	private Color color = Color.BLACK;
	private Difficulty difficulty;
	private AtomicBoolean active = new AtomicBoolean(false);
	private Question[][] questions;
	
	private static ArrayList<Player> activePlayers = new ArrayList<Player>();
}
