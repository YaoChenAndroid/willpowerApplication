package com.example.willpower.yuxin.models;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Question {
	private static String[] words=new String[]{"purple","green","blue","pink","brown","red","orange","yellow","white","black"};
	private static String[] colors=new String[]{"#7e1e9c","#15b01a","#0343df","#ff81c0","#653700","#e50000","#f97306","#ffff14","#ffffff","#000000"};
	private static String[] questions=new String[]{"choose the meaning of the word.","choose the color of the word."};
	private static HashMap<String, String> wordToColor=new HashMap<String,String>(){{
		for(int i=0;i<words.length;i++){
			put(words[i], colors[i]);
		}
	}};
	
	private String word;
	private String wordColor;
	private int questionType;
	private String[] colorOptions;
	
	public String getWord() {
		return word;
	}

	public String getWordColor() {
		return wordColor;
	}

	public String getQuestion() {
		return questions[questionType];
	}

	public String[] getColorOptions() {
		return colorOptions;
	}
	
	public boolean isCorrect(int i){
		if (questionType==0) {
			String meaningColor=wordToColor.get(word);
			if(colorOptions[i].equals(meaningColor)) return true;
			else return false;
		} else {
			if(colorOptions[i].equals(wordColor)) return true;
			else return false;
		}
	}
	
	public static Question newQuestion() {
		Question q=new Question();
		int i,j,k,l;
		i=(int)(Math.random()*words.length);
		j=(int)(Math.random()*words.length);
		for(;;){
			k=(int)(Math.random()*words.length);
			if((k!=i)&&(k!=j)) break;
		}
		l=(int)(Math.random()*questions.length);
		
		q.word=words[i];
		q.wordColor=colors[j];
		q.questionType=l;
		List list=Arrays.asList(new String[]{colors[i],colors[j],colors[k]});
		Collections.shuffle(list);
		q.colorOptions=(String[]) list.toArray(new String[0]);
		return q;
	}
	
	private Question(){}
}
