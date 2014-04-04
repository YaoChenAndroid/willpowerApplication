package com.example.willpower.yuxin.controllers;

import com.example.willpower.controllers.R;
import com.example.willpower.yuxin.models.Question;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ColorBrainActivity extends Activity {
	TextView cbtv1,cbtv2,cbtv3,cbtv4,cbtv5,cbtv6;
	Button cbbtn1,cbbtn2,cbbtn3;
	Button[] cbbtns;
	Question currentQuestion;
	int right=0;
	int wrong=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_colorbrain_yuxin);
		currentQuestion=Question.newQuestion();
		setupUI();
		refreshUI();
	}

	
	private void refreshUI() {
		cbtv1.setText(currentQuestion.getWord());
		cbtv1.setTextColor(Color.parseColor(currentQuestion.getWordColor()));
		cbtv2.setText(currentQuestion.getQuestion());
		for(int i=0;i<3;i++){
			cbbtns[i].setBackgroundColor(Color.parseColor(currentQuestion.getColorOptions()[i]));
		}
		cbtv4.setText(right+"");
		cbtv6.setText(wrong+"");
	}

	private void setupUI() {
		cbtv1=(TextView) findViewById(R.id.cbtv1);
		cbtv2=(TextView) findViewById(R.id.cbtv2);
		cbtv3=(TextView) findViewById(R.id.cbtv3);
		cbtv4=(TextView) findViewById(R.id.cbtv4);
		cbtv5=(TextView) findViewById(R.id.cbtv5);
		cbtv6=(TextView) findViewById(R.id.cbtv6);
		
		cbbtn1=(Button) findViewById(R.id.cbbtn1);
		cbbtn2=(Button) findViewById(R.id.cbbtn2);
		cbbtn3=(Button) findViewById(R.id.cbbtn3);
		
		cbbtns=new Button[]{cbbtn1,cbbtn2,cbbtn3};
		
		View.OnClickListener onClickListener1=new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				Button b=(Button) v;
				int i=0;
				for(;i<3;i++) if(b==cbbtns[i]) break;
				if (currentQuestion.isCorrect(i)) {
					right++;
				} else {
					wrong++;
				}
				currentQuestion=Question.newQuestion();
				refreshUI();				
			}
		};
		
		cbbtn1.setOnClickListener(onClickListener1);
		cbbtn2.setOnClickListener(onClickListener1);
		cbbtn3.setOnClickListener(onClickListener1);
	}
}
