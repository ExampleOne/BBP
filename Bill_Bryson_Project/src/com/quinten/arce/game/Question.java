package com.quinten.arce.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

import com.quinten.arce.Reference;

public class Question
{
	public static final String QUESTION_MARKER = "QUESTION=";
	public static final String ANSWER_MARKER = "ANSWER=";
	public static final String DIFFICULTY_MARKER = "DIFFICUTLY=";
	public static final String QUESTION_IMAGE_PATH_MARKER = "QUESTION_IMAGE_PATH=";
	public static final String ANSWER_IMAGE_PATH_MARKER = "ANSWER_IMAGE_PATH=";
	public static final String COMMENT_MARKER = "//";
	
	/**
	 * Constructor, however not expected to be used,
	 * allows for some null arguments.
	 * @param question canNOT be null.
	 * @param answers canNOT be null.
	 * @param difficultyLevel canNOT be null.
	 * @param questionImagePath
	 * @param answerImagePath
	 */
	public Question(String question, String[] answers, byte difficultyLevel, String[] questionImagePath, String[] answerImagePath)
	{
		this.question = question;
		this.answers = answers;
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
	
	public static Question[] getQuestions()
	{
		if(fileRead.compareAndSet(false, true))
			readQuestions();
		
		return Arrays.copyOf(questions.toArray(), questions.size(), Question[].class);
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
		
		while(true)
		{
			try
			{
				line = reader.readLine();
			} catch(IOException e)
			{
				break;
			}
			if(line == null) break;
			if(line.startsWith(COMMENT_MARKER)) continue;//ignores commented lines.
			
			if(line.toUpperCase().equals(QUESTION_MARKER) && !tempQuestionComponents.isEmpty())
			{
				questions.add(decode(tempQuestionComponents));
				tempQuestionComponents.clear();
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
		ArrayList<String> questionImages= new ArrayList<String>();
		ArrayList<String> answerImages = new ArrayList<String>();
		
		int j;
		String upper;
		for(String i : components)
		{
			j = 0;
			while(Character.isWhitespace(i.charAt(j))) j++;
			i = i.substring(j);
			upper = i.toUpperCase();
			if(upper.startsWith(QUESTION_MARKER)) result.question = i.substring(QUESTION_MARKER.length());
			else if(upper.startsWith(ANSWER_MARKER)) answers.add(i.substring(ANSWER_MARKER.length()));
			else if(upper.startsWith(DIFFICULTY_MARKER)) result.difficultyLevel = Byte.parseByte(i.substring(DIFFICULTY_MARKER.length()));
			else if(upper.startsWith(QUESTION_IMAGE_PATH_MARKER)) questionImages.add(i.substring(QUESTION_IMAGE_PATH_MARKER.length()));
			else if(upper.startsWith(ANSWER_IMAGE_PATH_MARKER)) answerImages.add(i.substring(ANSWER_IMAGE_PATH_MARKER.length()));
		}
		
		result.answerImagePaths = Arrays.copyOf(answerImages.toArray(), answerImages.size(), String[].class);
		result.questionImagePaths = Arrays.copyOf(questionImages.toArray(), questionImages.size(), String[].class);
		result.answers = Arrays.copyOf(answers.toArray(), questionImages.size(), String[].class);
		return result;
	}
	
	public final byte getDifficultyLevel()
	{
		return difficultyLevel;
	}
	
	public final String getQuestion()
	{
		return question;
	}
	
	public final String[] getAnswers()
	{
		return answers;
	}
	
	public final String[] getQuestionImagePaths()
	{
		return questionImagePaths;
	}
	
	public final String[] getAnswerImagePaths()
	{
		return answerImagePaths;
	}
	
	@Override
	public String toString()
	{
		return question;
	}
	
	private static ArrayList<Question> questions = new ArrayList<Question>(64);
	private static AtomicBoolean fileRead = new AtomicBoolean(false); 
	
	private String question = Reference.QUESTION_UNAVAILABLE;
	private String[] answers = {Reference.ANSWERS_UNAVAILANLE};
	private byte difficultyLevel;
	private String[] questionImagePaths = {};
	private String[] answerImagePaths = {};	
}
