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
	public static final String FINAL_IMAGE_PATH_MARKER = "FINAL IMAGE=";
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
	 * @param answerGroup canNOT be null.
	 * @param difficultyLevel canNOT be null.
	 * @param questionImagePath
	 * @param answerImagePath
	 */
	public Question(String question, String answer, ArrayList<Answer> wrongAnswers, byte difficultyLevel, 
			ArrayList<String> questionImagePath, ArrayList<String> answerImagePath)
	{
		this.question = question;
		Answer correctAnswer = new Answer(answer);
		this.answerGroup = new AnswerGroup(correctAnswer, wrongAnswers);
		this.difficultyLevel = difficultyLevel;
		if(questionImagePath != null)
			this.questionImagePaths = questionImagePath;
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
		Answer answer = new Answer();
		AnswerGroup answerGroup = new AnswerGroup(answer);
		answer.isCorrect = true;
		ArrayList<String> questionImages= new ArrayList<String>();
		String finalImagePath = "";
		
		Answer lastAnswer = answer;
		
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
			else if(upper.startsWith(ANSWER_MARKER))
			{
				answer.text = (i.substring(ANSWER_MARKER.length()));
				lastAnswer = answer;
			} else if(upper.startsWith(WRONG_ANSWER_MARKER)) 
			{
				answerGroup.addAnswer(new Answer(i.substring(WRONG_ANSWER_MARKER.length())));
				lastAnswer = answerGroup.getAnswers().get(answerGroup.getAnswers().size() - 1);
			} else if(upper.startsWith(DIFFICULTY_MARKER)) result.difficultyLevel = Byte.parseByte(i.substring(DIFFICULTY_MARKER.length()));
			else if(upper.startsWith(QUESTION_IMAGE_PATH_MARKER)) questionImages.add(i.substring(QUESTION_IMAGE_PATH_MARKER.length()));
			else if(upper.startsWith(ANSWER_IMAGE_PATH_MARKER))
			{
				lastAnswer.imageLocation = i.substring(ANSWER_IMAGE_PATH_MARKER.length());
			} else if(upper.startsWith(CATAGORY_MARKER)) result.catagory = Catagory.deserialise(i.substring(CATAGORY_MARKER.length()));
			else if(upper.startsWith(FINAL_IMAGE_PATH_MARKER))  finalImagePath = i.substring(FINAL_IMAGE_PATH_MARKER.length());
			else throw new IllegalArgumentException("Line: " + lineNum + " " + UNKNOWN_TOKEN_EXCEPTION + i);
		}
		
		result.questionImagePaths = questionImages;
		result.answerGroup = answerGroup;
		result.finalImagePath = finalImagePath;
		if(result.question.equals(Reference.QUESTION_UNAVAILABLE)) throw new IllegalArgumentException("Question missing question: " + result.question);
		else if(result.answerGroup == null) throw new IllegalArgumentException("Question missing answer: " + result.question);
		else if(result.catagory == null) throw new IllegalArgumentException("Question missing catagory: " + result.question);
		else if(result.answerGroup.getAnswers().isEmpty()) throw new IllegalArgumentException("Question missing wrong answers: " + result.question);
		else if(result.answerGroup.getAnswers().size() < Difficulty.highestDifficultyOf(result.getDifficultyLevel()).getNumAnswers())//throws error if invalid difficulty 
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
	
	public final Answer getCorrectAnswer()
	{
		return answerGroup.getCorrectAnswer();
	}
	
	public final ArrayList<String> getQuestionImagePaths()
	{
		return questionImagePaths;
	}
	
	public final Catagory getCatagory()
	{
		return catagory;
	}
	
	public final ArrayList<Answer> getWrongAnswers()
	{
		return answerGroup.getWrongAnswers();
	}
	
	public final String getFinalImagePath()
	{
		return finalImagePath;
	}
	
	public final AnswerGroup getAnswerGroup()
	{
		return answerGroup;
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
	private AnswerGroup answerGroup;
	private String finalImagePath;
	private byte difficultyLevel = DEFAULT_DIFFICULTY_LEVEL;
	private ArrayList<String> questionImagePaths;
	private Catagory catagory;
}
