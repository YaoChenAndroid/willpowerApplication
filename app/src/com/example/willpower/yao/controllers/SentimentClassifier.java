package com.example.willpower.yao.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.res.AssetManager;
import android.os.Environment;
import android.util.Log;

import com.aliasi.classify.Classification;
import com.aliasi.classify.Classified;
import com.aliasi.classify.ConditionalClassification;
import com.aliasi.classify.DynamicLMClassifier;
import com.aliasi.classify.LMClassifier;
import com.aliasi.corpus.ObjectHandler;
import com.aliasi.util.AbstractExternalizable;
import com.aliasi.util.Compilable;
import com.aliasi.util.Files;

public class SentimentClassifier {
	private final static String TAG = "SentimentClassifier";
	String[] categories;
	LMClassifier temp;

	public SentimentClassifier() {
	
	try {
		temp= (LMClassifier) AbstractExternalizable.readObject(new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/classifier.txt"));
		categories = temp.categories();
	}
	catch (ClassNotFoundException e) {
		e.printStackTrace();
	}
	catch (IOException e) {
		e.printStackTrace();
	}

	}

	public String classify(String text) {
	ConditionalClassification classification = temp.classify(text);
	return classification.bestCategory();
	}
	void train() throws IOException, ClassNotFoundException {
		File trainDir;
		String[] categories;
		LMClassifier temp;
		trainDir = new File("trainDirectory");
		categories = trainDir.list();
		int nGram = 7; //the nGram level, any value between 7 and 12 works
		temp= DynamicLMClassifier.createNGramProcess(categories, nGram);

		for (int i = 0; i < categories.length; ++i) {
			String category = categories[i];
			Classification classification = new Classification(category);
			File file = new File(trainDir, categories[i]);
			File[] trainFiles = file.listFiles();
			for (int j = 0; j < trainFiles.length; ++j) {
				File trainFile = trainFiles[j];
				String review = Files.readFromFile(trainFile, "ISO-8859-1");
				Classified classified = new Classified(review, classification);
				((ObjectHandler) temp).handle(classified);  
			}
	 	}
		AbstractExternalizable.compileTo((Compilable) temp, new File("classifier.txt"));
	}
}