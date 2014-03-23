package com.example.willpower.yuxin.models;

public class Question {
	private String word;
	private String wordColor;
	private String question;
	private String[] colorOptions;
	
	public String getWord() {
		return word;
	}

	public String getWordColor() {
		return wordColor;
	}

	public String getQuestion() {
		return question;
	}

	public String[] getColorOptions() {
		return colorOptions;
	}
	
	public boolean isCorrect(int i){
		return true;
	}
	
	public static Question newQuestion() {
		Question q=new Question();
		q.word="Yellow";
		q.wordColor="#ffffff";
		q.question="choose the meaning of the word.";
		q.colorOptions=new String[]{"#F7F700","#000000","#ffffff"};
		return q;
	}
	
	private Question(){}
}
