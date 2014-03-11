package com.quinten.arce.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import com.badlogic.gdx.graphics.Texture;
import com.quinten.arce.Reference;

public class Question
{
	public static final String QUESTION_MARKER = "QUESTION=";
	public static final String ANSWER_MARKER = "ANSWER=";
	public static final String WRONG_ANSWER_MARKER = "WRONG ANSWER=";
	public static final String DIFFICULTY_MARKER = "DIFFICULTY=";
	public static final String QUESTION_IMAGE_PATH_MARKER = "QUESTION IMAGE PATH=";
	public static final String ANSWER_IMAGE_PATH_MARKER = "ANSWER IMAGE PATH=";
	public static final String CATAGORY_MARKER = "CATAGORY=";
	public static final String COMMENT_MARKER = "//";
	
	public static final String UNKNOWN_TOKEN_EXCEPTION = "Unknown token: ";
	public static final byte DEFAULT_DIFFICULTY_LEVEL = -128;
	
	/**
	 * Constructor, however not expected to be used,
	 * allows for some null arguments.
	 * @param question canNOT be null.
	 * @param answers canNOT be null.
	 * @param difficultyLevel canNOT be null.
	 * @param questionImagePath
	 * @param answerImagePath
	 */
	public Question(String question, ArrayList<String> answers, ArrayList<String> wrongAnswers, byte difficultyLevel, 
			ArrayList<String> questionImagePath, ArrayList<String> answerImagePath)
	{
		this.question = question;
		this.answers = answers;
		this.wrongAnswers = wrongAnswers;
		this.difficultyLevel = difficultyLevel;
		if(questionImagePath != null)
			this.questionImagePaths = questionImagePath;
		if(questionImagePath != null)
			this.answerImagePaths = answerImagePath;
	}
	
	/**
	 * Allows for initialisation without
	 * any details. Should only be
	 * used privately when decoding.
	 */
	private Question() {}
	
	public static ArrayList<Question> getQuestions()
	{
		if(fileRead.compareAndSet(false, true))
			readQuestions();
		
		return questions;
	}
	
	/**
	 * Reads the questions from the file.
	 * no need for relies on atomicBoolean
	 * to be called, and  
	 * should only be called once.
	 */
	private static void readQuestions()
	{
		BufferedReader reader = new BufferedReader(Reference.QUESTIONS_FILEHANDLE.reader());
		ArrayList<String> tempQuestionComponents = new ArrayList<String>();
		String line = "";
		int lineNum = 0;
		
		while(true)
		{
			lineNum++;
			try
			{
				line = reader.readLine();
			} catch(IOException e)
			{
				break;
			}
			if(line == null) break;
			if(line.startsWith(COMMENT_MARKER) || isWhiteSpace(line)) continue;//ignores commented lines.
			
			if(line.toUpperCase().equals(QUESTION_MARKER) && !tempQuestionComponents.isEmpty())
			{
				questions.add(decode(tempQuestionComponents));
				tempQuestionComponents.clear();
				line = lineNum + line;
			}
			tempQuestionComponents.add(line);
		}
		questions.add(decode(tempQuestionComponents));
		tempQuestionComponents = null;
	}
	
	private static Question decode(ArrayList<String> components)
	{
		Question result = new Question();
		ArrayList<String> answers = new ArrayList<String>();
		ArrayList<String> wrongAnswers = new ArrayList<String>();
		ArrayList<String> questionImages= new ArrayList<String>();
		ArrayList<String> answerImages = new ArrayList<String>();
		
		String lineNum;
		int j;
		String upper;
		for(String i : components)
		{
			lineNum = "";
			j = 0;
			while(Character.isDigit(i.charAt(j)))
			{
				lineNum += i.charAt(j++);
			}
			while(Character.isWhitespace(i.charAt(j))) j++;
			
			i = i.substring(j);
			upper = i.toUpperCase();
			if(upper.startsWith(QUESTION_MARKER)) result.question = i.substring(QUESTION_MARKER.length());
			else if(upper.startsWith(ANSWER_MARKER)) answers.add(i.substring(ANSWER_MARKER.length()));
			else if(upper.startsWith(WRONG_ANSWER_MARKER)) wrongAnswers.add(i.substring(WRONG_ANSWER_MARKER.length()));
			else if(upper.startsWith(DIFFICULTY_MARKER)) result.difficultyLevel = Byte.parseByte(i.substring(DIFFICULTY_MARKER.length()));
			else if(upper.startsWith(QUESTION_IMAGE_PATH_MARKER)) questionImages.add(i.substring(QUESTION_IMAGE_PATH_MARKER.length()));
			else if(upper.startsWith(ANSWER_IMAGE_PATH_MARKER)) answerImages.add(i.substring(ANSWER_IMAGE_PATH_MARKER.length()));
			else if(upper.startsWith(CATAGORY_MARKER)) result.catagory = Catagory.deserialise(i.substring(CATAGORY_MARKER.length()));
			else throw new IllegalArgumentException("Line: " + lineNum + " " + UNKNOWN_TOKEN_EXCEPTION + i);
		}
		
		result.answerImagePaths = answerImages;
		result.questionImagePaths = questionImages;
		result.answers = answers;
		result.wrongAnswers = wrongAnswers;
		if(result.question.equals(Reference.QUESTION_UNAVAILABLE)) throw new IllegalArgumentException("Question missing question: " + result.question);
		else if(result.answers == null) throw new IllegalArgumentException("Question missing answer: " + result.question);
		else if(result.catagory == null) throw new IllegalArgumentException("Question missing catagory: " + result.question);
		else if(result.wrongAnswers == null) throw new IllegalArgumentException("Question missing wrong answers: " + result.question);
		else if(result.wrongAnswers.size() +  1 < Difficulty.highestDifficultyOf(result.getDifficultyLevel()).getNumAnswers())//throws error if invalid difficulty 
			throw new IllegalArgumentException("Question lacking wrong answers: " + result.question);
		else return result;
	}
	
	public static boolean isWhiteSpace(String string)
	{
		for(int i = 0, length = string.length(); i < length; i++)
		{
			if(!Character.isWhitespace(string.charAt(i))) return false; 
		}
		return true;
	}
	
	public final byte getDifficultyLevel()
	{
		return difficultyLevel;
	}
	
	public final String getQuestion()
	{
		return question;
	}
	
	public final ArrayList<String> getAnswers()
	{
		return answers;
	}
	
	public final ArrayList<String> getQuestionImagePaths()
	{
		return questionImagePaths;
	}
	
	public final ArrayList<String> getAnswerImagePaths()
	{
		return answerImagePaths;
	}
	
	public final Catagory getCatagory()
	{
		return catagory;
	}
	
	public final ArrayList<String> getWrongAnswers()
	{
		return wrongAnswers;
	}
	
	@Override
	public String toString()
	{
		return question;
	}
	
	public static Texture getQuestionImage(String path)
	{
		return new Texture(Reference.QUESTION_IMAGE_LOC + path);
	}
	
	public static Texture getAnswerImage(String path)
	{
		return new Texture(Reference.ANSWER_IMAGE_LOC + path);
	}
	
	private static ArrayList<Question> questions = new ArrayList<Question>(64);
	private static AtomicBoolean fileRead = new AtomicBoolean(false); 
	
	private String question = Reference.QUESTION_UNAVAILABLE;
	private ArrayList<String> answers;
	private ArrayList<String> wrongAnswers;
	private byte difficultyLevel = DEFAULT_DIFFICULTY_LEVEL;
	private ArrayList<String> questionImagePaths;
	private ArrayList<String> answerImagePaths;
	private Catagory catagory;
}
