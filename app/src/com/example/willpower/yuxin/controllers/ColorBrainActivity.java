package com.example.willpower.yuxin.controllers;

import java.util.List;

import com.example.willpower.controllers.MainActivity;
import com.example.willpower.controllers.R;
import com.example.willpower.yuxin.models.Question;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class ColorBrainActivity extends Activity {
    public static final String PREFS_NAME = "ColorBrainActivity";

	TextView cbtv1,cbtv2,cbtv3,cbtv4,cbtv5,cbtv6,cbtv7,cbtv8;
	TextView cbtimer;
	ProgressBar progressBar;
	long timelimit=30*1000;
	Button cbbtn1,cbbtn2,cbbtn3;
	Button[] cbbtns;
	Question currentQuestion;
	int right=0;
	int wrong=0;
	int highestScore;
	MyCount timerCount;
	long millisUntilFinished=timelimit;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_colorbrain_yuxin);
		currentQuestion=Question.newQuestion();
	    //SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
	    //highestScore=settings.getInt("highestScore", 0);
		
		//resetHighestScore();
		highestScore=(Integer)getIntent().getExtras().get("CBH");
		setupUI();
		refreshUI();
	    //timerCount = new MyCount(timelimit, 1000);
	    //timerCount.start();

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
		cbtv8.setText(highestScore+"");
		progressBar.setMax(100);
	}

	private void setupUI() {
		progressBar=(ProgressBar) findViewById(R.id.progressBar1);
		cbtimer=(TextView) findViewById(R.id.cbtimer);
		cbtv1=(TextView) findViewById(R.id.cbtv1);
		cbtv2=(TextView) findViewById(R.id.cbtv2);
		cbtv3=(TextView) findViewById(R.id.cbtv3);
		cbtv4=(TextView) findViewById(R.id.cbtv4);
		cbtv5=(TextView) findViewById(R.id.cbtv5);
		cbtv6=(TextView) findViewById(R.id.cbtv6);
		cbtv7=(TextView) findViewById(R.id.cbtv7);
		cbtv8=(TextView) findViewById(R.id.cbtv8);
		
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
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		timerCount.cancel();
		
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		refreshUI();
 	    timerCount = new MyCount(millisUntilFinished, 1000);
	    timerCount.start();	   
	}
	  public class MyCount extends CountDownTimer {
	      public MyCount(long millisInFuture, long countDownInterval) {
	        super(millisInFuture, countDownInterval);
	      }

	      @Override
	      public void onFinish() {
	    	  
		    	cbtimer.setText("0");

	    	// 1. Instantiate an AlertDialog.Builder with its constructor
	    	  AlertDialog.Builder builder = new AlertDialog.Builder(ColorBrainActivity.this);

	    	  // 2. Chain together various setter methods to set the dialog characteristics
	    	  if(right-wrong>highestScore){
		    	  builder.setMessage("Congratulations!You have made a new record of "+(right-wrong)+" points!");
		    	  //getSharedPreferences(PREFS_NAME, 0).edit().putInt("highestScore", highestScore).commit();
		    	  updateHighestScore(right-wrong);
	    	  }else{
		    	  builder.setMessage("You score "+(right-wrong)+" points, keep practicing!");
	    	  }

	    	// Add the buttons
	    	  builder.setPositiveButton("exit", new DialogInterface.OnClickListener() {
	    	             public void onClick(DialogInterface dialog, int id) {
	    	            	 ColorBrainActivity.this.finish();
	    	             }
	    	         });
	    	  builder.setNegativeButton("restart", new DialogInterface.OnClickListener() {
	    	             public void onClick(DialogInterface dialog, int id) {
	    	            	right=0;
	    	            	wrong=0;
	    	        		refreshUI();
	    	         	    timerCount = new MyCount(timelimit, 1000);
	    	        	    timerCount.start();	    	            	 
	    	             }
	    	         });
	    	  // 3. Get the AlertDialog from create()
	    	  builder.setCancelable(false);     
	    	  AlertDialog dialog = builder.create();
	    	  dialog.setCanceledOnTouchOutside(false);
	    	  dialog.show();
	      }

	      @Override
	      public void onTick(long millisUntilFinished) {
	    	  ColorBrainActivity.this.millisUntilFinished=millisUntilFinished;
	    	progressBar.setProgress(100-(int)(millisUntilFinished*100/timelimit));//((int)millisUntilFinished/30000);
		    //Toast.makeText(ColorBrainActivity.this, (millisUntilFinished/1000)+"", Toast.LENGTH_SHORT).show();
	    	cbtimer.setText((millisUntilFinished/1000)+"");
	      }   
	    } 
	  
	  
	 /* private void resetHighestScore(){
		  ParseUser currentUser = ParseUser.getCurrentUser();
		  if (currentUser == null) {
			  SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
			    highestScore=settings.getInt("highestScore", 0);
		  } else {
			  highestScore=currentUser.getInt("CBHighestScore");
		  }
	  }*/
	  
	  private void updateHighestScore(int score){
		  highestScore=score;
		  final ParseUser currentUser = ParseUser.getCurrentUser();
		  if (currentUser == null) {
			  getSharedPreferences(PREFS_NAME, 0).edit().putInt("highestScore", score).commit();
		  } else {
				ParseQuery<ParseObject> query = ParseQuery.getQuery("GameData");
				query.whereEqualTo("userObjectId", currentUser.getObjectId());
				query.findInBackground(new FindCallback<ParseObject>() {

					@Override
					public void done(List<ParseObject> arg0, ParseException arg1) {
						// TODO Auto-generated method stub
						arg0.get(0).put("CBH", highestScore);
						arg0.get(0).saveInBackground();
					}
					
				});
		  }		  
	  }
}
